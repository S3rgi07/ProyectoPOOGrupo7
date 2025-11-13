package ui.controllers;

import model.Estudiante;
import service.UVRateService;

public interface SubControlador {
    void setContext(Estudiante estudiante, UVRateService service);

    void setDashboard(DashboardController dashboard);
}
