package br.com.imobiliaria.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Foto;
import br.com.imobiliaria.modelo.TipoFoto;
import br.com.imobiliaria.util.ContextUtil;

@SessionScoped
@ManagedBean
public class FileUploadBean {
	private DAO<Foto> dao = new DAO<Foto>(Foto.class);
	private Long id;
	private TipoFoto tipo;
	private String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/fotos/");
	public Long getId() {
		return id;
	}
	
	public TipoFoto getTipo() {
		return tipo;
	}
	
	public void uploadCondominio(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		CondominioBean cond = (CondominioBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						"#{condominioBean}", CondominioBean.class)
				.getValue(context.getELContext());
		
		id = cond.getCondominio().getId();
		
		tipo = TipoFoto.Condomínio;
		
		try {
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadTorre(FileUploadEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		TorreBean torre = (TorreBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						"#{torreBean}", TorreBean.class)
				.getValue(context.getELContext());
		
		id = torre.getTorre().getId();
		
		tipo = TipoFoto.Torre;
		
		try {
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadApartamento(FileUploadEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ApartamentoBean apartamento = (ApartamentoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						"#{apartamentoBean}", ApartamentoBean.class)
				.getValue(context.getELContext());
		
		id = apartamento.getApartamento().getId();
		
		tipo = TipoFoto.Apartamento;
		
		try {
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {
		try {
			Foto f = new Foto();
			f.setLocal(destination);
			f.setIdVinculado(id);
			f.setTipo(tipo);
			
			dao.adiciona(f);
			
			OutputStream out = new FileOutputStream(new File(destination + "/" + f.getId()));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
			
			ContextUtil.getAnyMessage("Foto gravada com sucesso!");

		}catch (IOException e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
			System.out.println(e.getMessage());
		}catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
			System.out.println(e.getMessage());
		}
	}
}