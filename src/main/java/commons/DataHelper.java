package commons;

import java.util.Locale;

import com.github.javafaker.Faker;

public class DataHelper {
private Locale locale = new Locale("en");
private Faker faker = new Faker(locale);
public static DataHelper getdata() {
	return new DataHelper();
}
public String getFullName() {
	return faker.name().fullName();
}
public String getFirstName() {
	return faker.address().firstName();
}
public String getLastName() {
	return faker.address().lastName();
}
public String getCompanyName() {
	return faker.company().name();
}
public String getAddress() {
	return faker.address().streetAddress();
}
public String getEmail() {
	return faker.internet().emailAddress();
}
public String getPhone() {
	return faker.phoneNumber().phoneNumber();
}
public String getCity() {
	return faker.address().city();
}
public String getCityName() {
	return faker.address().cityName();
}
public String getPassword() {
	return faker.internet().password(6, 10);
}
}
