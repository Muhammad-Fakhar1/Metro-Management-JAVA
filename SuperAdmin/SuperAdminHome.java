package com.metro.SuperAdmin;

import com.metro.Components.BaseFrame;
import com.metro.Models.Employee;

public class SuperAdminHome extends BaseFrame{
    
    private SuperAdminBody sab;
    private Employee superAdmin;
    
    public SuperAdminHome(Employee superAdmin) {
        super("Super Admin",superAdmin.getName(),"", 0.80, 0.75);
        this.superAdmin=superAdmin;
        sab=new BranchesUI(superAdmin,body.getWidth(), body.getHeight());
        updateBody(sab);    
    }

    @Override
    protected void setSidebar() {
        sidebar.setVisible(false);
    }   
    
    private void updateBody(SuperAdminBody newPanel) {
        sab = newPanel;
        body.add(sab);
        body.setViewportView(sab);
        body.repaint();
        body.revalidate();
    }
}
