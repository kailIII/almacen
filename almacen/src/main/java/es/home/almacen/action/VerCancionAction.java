/**
 * 
 */
package es.home.almacen.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import es.home.almacen.bean.Cancion;
import es.home.almacen.bean.Disco;
import es.home.almacen.business.CancionBO;
import es.home.almacen.business.DiscoBO;
import es.home.almacen.form.VerCancionForm;
import es.home.almacen.util.Constante;

/**
 * @author dsblanco
 * 
 */
public class VerCancionAction extends Action {

    private CancionBO cancionBO;
    private DiscoBO discoBO;

    private final static Logger LOGGER = Logger.getLogger(VerCancionAction.class);

    public VerCancionAction() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.apache.struts.action.Action#execute(org.apache.struts.action.
     * ActionMapping, org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	String forward = Constante.FW_SUCCESS;
	LOGGER.debug("Inicio VerCancionAction");
	try {
	    VerCancionForm vdForm = (VerCancionForm) form;
	    Integer idcancion = vdForm.getIdcancion();
	    Cancion cancion = null;
	    if (idcancion == null || idcancion.intValue() == 0) {
		ActionErrors errors = new ActionErrors();
		ActionMessage error = new ActionMessage(Constante.KEY_KO_IDGRUPO);
		errors.add(Constante.MSG_KO, error);
		saveErrors(request, errors);
	    } else {
		cancion = cancionBO.obtenerCancion(idcancion);
	    }

	    if (cancion == null) {
		// Ha ocurrido un error
		ActionErrors errors = new ActionErrors();
		ActionMessage error = new ActionMessage(Constante.KEY_KO_ACTION);
		errors.add(Constante.MSG_KO, error);
		saveErrors(request, errors);
	    } else {
		vdForm.setCancion(cancion);
	    }

	    // Obtenemos el conjunto de discos
	    List<LabelValueBean> lista = new ArrayList<LabelValueBean>();

	    List<Disco> listaDiscos = discoBO.buscarDisco(null, null, null, 0);
	    if (!listaDiscos.isEmpty()) {
		for (Disco disco : listaDiscos) {
		    lista.add(new LabelValueBean(disco.getNombre(), Integer.toString(disco.getIddisco())));
		}
	    }
	    vdForm.setDiscos(lista);
	} catch (Exception except) {
	    LOGGER.error("Error al ejecutar el action", except);
	    forward = Constante.FW_ERROR_FATAL;
	}
	LOGGER.debug("End VerCancionAction");
	return mapping.findForward(forward);

    }

    public CancionBO getCancionBO() {
	return cancionBO;
    }

    public DiscoBO getDiscoBO() {
	return discoBO;
    }

    public void setCancionBO(final CancionBO cancionBO) {
	this.cancionBO = cancionBO;
    }

    public void setDiscoBO(final DiscoBO discoBO) {
	this.discoBO = discoBO;
    }
}
