package tests;


import com.github.javafaker.Faker;


public class FakeUserData {
   // GetAllPostsResponse getAllPostsResponse=new GetAllPostsResponse();
    Faker faker = new Faker();

    String fakeEmail = faker.internet().emailAddress();
    String fakePassword = faker.regexify("[aK][io][l][t][u][0-9]{8,10}");
    String confirmPassword = fakePassword;
    String userRole = "user";
    String adminRole = "admin";
    String fakeName = faker.name().firstName();
    String fakeSurname = faker.name().lastName();
    String fakePhoneNumber = faker.regexify("[1-9]{8,9}");
    String fakeTitle=faker.lorem().characters(5,40);

    String fakeTitleOverMaxLength=faker.lorem().characters(41,50);
    String fakeDescription=faker.lorem().characters(2,100);
    String fakeDescriptionOverMaxLength=faker.lorem().characters(101,120);
    String fakeMyThoughts=faker.lorem().characters(2,1000);
    String fakeMyThoughtsOverMaxLength=faker.lorem().characters(1001,1005);


    String fakeBirthdate = String.valueOf(faker.date().birthday(7, 100));

    String emailAdmin = "katja@th.lv";
    String passwordAdmin = "katja1981";
    String emailUser = "sobachka@kl.lo";
    String passwordUser = "sobachka231";
}