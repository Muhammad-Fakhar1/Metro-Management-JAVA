package com.metro.SuperAdmin;

import com.metro.Sections.BranchesUI;
import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import com.metro.Models.Employee;

public class SuperAdminHome extends BaseFrame{
    
    private Body sab;
    private Employee superAdmin;
    
    public SuperAdminHome(Employee superAdmin) {
        super("Super Admin",superAdmin,"", 0.80, 0.75);
        this.superAdmin=superAdmin;
        sab=new BranchesUI(superAdmin,body.getWidth(), body.getHeight());
        updateBody(sab);    
    }

    @Override
    protected void setSidebar() {
        sidebar.setVisible(false);
    }   
    
    private void updateBody(Body newPanel) {
        sab = newPanel;
        body.add(sab);
        body.setViewportView(sab);
        body.repaint();
        body.revalidate();
    }
}
