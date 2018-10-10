package Controller;

import Model.Model;
import Model.JdbcModel;
import Model.Validator;
import View.View;
import View.GsonView;
import io.javalin.Javalin;
import org.eclipse.jetty.server.Server;

public class MyApp {

  public static void main(String[] args) {

    Javalin app = Javalin.create();

    app.server(() -> {
      Server server = new Server();
      server.setStopTimeout(3);
      return server;
    });

    app.start(7000);

    View gson = new GsonView();
    Model jdbc = new JdbcModel("jdbc:mysql://127.0.0.1:3306/MembershipSystem", "root", "");

    Validator validate = new Validator(jdbc);

    Registration registration = new Registration(gson, jdbc, validate);
    Edit edit = new Edit(gson, jdbc, validate);
    Detail detail = new Detail(gson, jdbc, validate);
    Present present = new Present(gson, jdbc, validate);

    app.get("present/:id", present::handlePresentation);

    app.get("/cards/:id", detail::handleCardDetail);
    app.get("/employees/", detail::handleEmployeesList);
    app.get("/employees/:id", detail::handleEmployeeDetail);

    app.post("/cards", registration::handleCardRegistration);

    app.put("/employees/:id", edit::handleEdit);
    app.put("/cards/:id", edit::handleTopUp);

  }

}
