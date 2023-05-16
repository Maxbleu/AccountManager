package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import org.apache.log4j.Logger;

public class MainController {

    //  COMPONENTES GRÁFICOS

    //  DEPENDENCES

    //  Logger lo utilizamos para informar al usuario de lo que ocurre en la aplicación
    private final Logger logger;

    //  CAMPOS

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public MainController(){
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS
}