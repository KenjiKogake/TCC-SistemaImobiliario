package br.com.imobiliaria.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imobiliaria.modelo.Entity;

@FacesConverter(value="entityConverter", forClass=Entity.class)
public class EntityConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")) return this.getAttributesFrom(component).get(value);

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null && !value.equals("")){
			Entity entity = (Entity) value;
			
			if(entity.getId() != null){
				this.addAttribute(component, entity);
				
				if(entity.getId() != null){
					return String.valueOf(entity.getId());
				}
				return (String) value;
			}
		}
		return "";
	}
	
	private void addAttribute(UIComponent component, Entity entity) {
		this.getAttributesFrom(component).put(entity.getId().toString(), entity);
	}

	private Map<String, Object> getAttributesFrom(UIComponent component){
		return component.getAttributes();
	}

}
