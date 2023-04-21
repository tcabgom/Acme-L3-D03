package acme.features.student.enrolment;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

    @Autowired
    protected StudentEnrolmentRepository repository;

    @Override
    public void check() {
        boolean status;

        status = super.getRequest().hasData("id", int.class);

        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        int id = super.getRequest().getData("id", int.class);
        Enrolment object = this.repository.findById(id);

        boolean status = !object.isFinished();
        super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
        int id = super.getRequest().getData("id", int.class);
        Enrolment object = this.repository.findById(id);

        super.getBuffer().setData(object);
    }

    @Override
    public void bind(Enrolment object) {
        super.bind(object, "creditCardHolder", "creditCardNibble");

    }

    @Override
    public void validate(Enrolment object) {
        assert object != null;

        String expirationDate = super.getRequest().getData("expirationDate", String.class);
        Integer securityCode = super.getRequest().getData("securityCode", Integer.class);
        Date parsedExpirationDate = parseExpirationDate(expirationDate);

        boolean filledCardHolder = object.getCreditCardHolder() != null && !object.getCreditCardHolder().isEmpty();
        boolean filledCardNumber = object.getCreditCardNibble() != null && !object.getCreditCardNibble().isEmpty();
        boolean filledSecurityCode = securityCode != null;
        boolean filledExpirationDate = expirationDate != null;
        boolean validCardNumber = isValidCreditCardNumber(object.getCreditCardNibble());
        boolean validSecurityCode = filledSecurityCode && securityCode.toString().length() == 3;
        boolean validExpirationDate = checkExpirationDate(expirationDate);
        boolean expirationDateInFuture = validExpirationDate && MomentHelper.isFuture(parsedExpirationDate);

        if(!super.getBuffer().getErrors().hasErrors("creditCardHolder")) {
            super.state(filledCardHolder, "creditCardHolder", "javax.validation.constraints.NotEmpty.message");
        }
        if(!super.getBuffer().getErrors().hasErrors("creditCardNibble")) {
            super.state(validCardNumber, "creditCardNibble", "org.hibernate.validator.constraints.CreditCardNumber.message");
            super.state(filledCardNumber, "creditCardNibble", "javax.validation.constraints.NotEmpty.message");
        }

        if(!super.getBuffer().getErrors().hasErrors("expirationDate")) {
            super.state(filledExpirationDate, "expirationDate", "javax.validation.constraints.NotEmpty.message");
            super.state(validExpirationDate, "expirationDate", "student.enrolment.form.error.invalidExpirationDate");
            super.state(expirationDateInFuture, "expirationDate", "javax.validation.constraints.Future.message");
        }

        if(!super.getBuffer().getErrors().hasErrors("securityCode")) {
            super.state(filledSecurityCode, "securityCode", "javax.validation.constraints.NotEmpty.message");
            super.state(validSecurityCode, "securityCode", "student.enrolment.form.error.invalidSecurityCode");
        }
    }

    @Override
    public void perform(Enrolment object) {
        assert object != null;
        String creditCardNumber = object.getCreditCardNibble();
        String creditCardNibble = object.getCreditCardNibble().trim().substring(creditCardNumber.length() - 4);
        object.setCreditCardNibble(creditCardNibble);
        object.setFinished(true);
        this.repository.save(object);
    }

    @Override
    public void unbind(Enrolment object) {
        assert object != null;

        Tuple tuple;

        tuple = super.unbind(object, "code", "motivation", "goals", "creditCardHolder", "creditCardNibble");
        tuple.put("readonly", object.isFinished());

        super.getResponse().setData(tuple);
    }

    public static boolean isValidCreditCardNumber(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }

        // Using Luhn's Algorithm given that @CreditCardNumber uses it for validation
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }


    private boolean checkExpirationDate(String expirationDate) {
        final String dateRegex = "^(0[1-9]|1[0-2])\\/\\d{2}|\\d{2}\\/(0[1-9]|1[0-2])$";

        return expirationDate != null && expirationDate.matches(dateRegex);

    }

    private Date parseExpirationDate(String expirationDate) {
        Date parsedDate;

        if(expirationDate == null || expirationDate.isEmpty() || !checkExpirationDate(expirationDate)) {
            return null;
        }

        try {
            parsedDate = MomentHelper.parse("MM/yy", expirationDate);
        } catch (Exception e) {
            parsedDate = MomentHelper.parse("yy/MM", expirationDate);
        }
        return parsedDate;
    }
}
