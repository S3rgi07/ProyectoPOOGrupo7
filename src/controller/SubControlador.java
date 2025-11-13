package controller;

import model.Estudiante;
import service.UVRateService;

public interface SubControlador {
    void setContext(Estudiante estudiante, UVRateService service);
}
