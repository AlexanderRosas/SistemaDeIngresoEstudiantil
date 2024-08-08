import UserInterface.Form.SplashScreenForm;
import UserInterface.Form.StudentDataEntryForm;

public class App {
    public static void main(String[] args) throws Exception {
        SplashScreenForm.show();
        Thread.sleep(7000);
        StudentDataEntryForm frmMain = new StudentDataEntryForm();
        frmMain.setVisible(true);
    }
}