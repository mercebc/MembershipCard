package Controller;

import Model.Model;
import Model.JdbcModel;
import View.View;
import View.GsonView;
import io.javalin.Javalin;

public class MyApp {

  public static void main(String[] args) {

    Javalin app = Javalin.create().start(7000);

    View gson = new GsonView();
    Model jdbc = new JdbcModel("jdbc:mysql://127.0.0.1:3306/MembershipSystem", "root", "");

    Registration registration = new Registration(gson, jdbc);
    Edit edit = new Edit(gson, jdbc);
    Detail detail = new Detail(gson, jdbc);

    app.get("/cards/:id", detail::handleCardDetail);
    app.get("/employees/", detail::handleEmployeesList);
    app.get("/employees/:id", detail::handleEmployeeDetail);

    app.post("/cards", registration::handleCardRegistration);

    app.put("/employees/:id", edit::handleEdit);
    app.put("/cards/:id", edit::handleTopUp);

  }

}
