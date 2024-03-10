package Negocio;

import Datos.DPago;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NPago {

    private final DPago dPago;

    public NPago() throws SQLException {
        this.dPago = new DPago();
    }

    public void addPago(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dPago.save(new DPago(
                Float.parseFloat(parametros.get(0)),
                parametros.get(1),
                Integer.parseInt(parametros.get(2)))
        );
    }

    public void editPago(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dPago.update(
                Integer.parseInt(parametros.get(0)),
                new DPago(
                    Float.parseFloat(parametros.get(1)),
                    parametros.get(2),
                    Integer.parseInt(parametros.get(3))
                )
        );
    }

    public void delPago(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dPago.delete(Integer.parseInt(parametros.get(0)));
    }

    public String[] getPagoById(List<String> parametros) throws SQLException {
        return this.dPago.getById(Integer.parseInt(parametros.get(0)));
    }

    public String[] getLastPaymentId() throws SQLException {
        return this.dPago.getLastPaymentId();
    }

    public ArrayList<String[]> getPagos() throws SQLException {
        return dPago.getAll();
    }
}
