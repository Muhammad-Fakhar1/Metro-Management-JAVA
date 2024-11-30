package com.metro.SuperAdmin;

import com.metro.BaseFrame;

public class SuperAdminHome extends BaseFrame{
    
    private SuperAdminBody sab;
    
    public SuperAdminHome() {
        super("Super Admin","Muhammad Fakhar bin Rashid","", 0.80, 0.75);
        sab=new BranchesUI(body.getWidth(), body.getHeight());
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
