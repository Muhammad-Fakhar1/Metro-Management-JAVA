package BranchManager;

import com.metro.BaseFrame;
import javax.swing.ImageIcon;

public class BranchManagerDashboard extends BaseFrame {

    private branchManagerBody bmd;

    public BranchManagerDashboard() {
        super("Branch Manager", 0.80, 0.75);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> System.out.println("home clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Employee", new ImageIcon("images/users.png"), e -> updateBody(new EmployeeUI(body.getWidth(), body.getHeight())));
        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }

    private void updateBody(branchManagerBody newPanel) {
        bmd = newPanel;
        body.add(bmd);
        body.setViewportView(bmd);
        body.repaint();
        body.revalidate();
    }
}
