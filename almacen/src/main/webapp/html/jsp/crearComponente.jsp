<%@ include file="/html/jsp/comun/import.jsp" %>
<div id="formulario">
<html:form action="/crearComponente.do">
	<div class="fieldset">
		<label for="nombre" ><bean:message key="general.nombre"/><c:out value=":"/></label>
		<html:text property="nombre" name="crearComponenteForm"  styleId="nombre"/><br/>
	</div>
	<br/>
	<button type="submit" class="positive">
		<img src='html/img/apply2.png' alt='guardar'/>
		<bean:message key="general.guardar"/>
	</button>
</html:form>
</div>