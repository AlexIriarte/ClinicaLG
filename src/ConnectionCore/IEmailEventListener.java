/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionCore;

import Utils.Correo;
import java.util.List;

public interface IEmailEventListener {
    void onReceiveEmailEvent(List<Correo> emails);
}
