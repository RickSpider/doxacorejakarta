package com.doxacore.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.zkoss.zul.South;

import com.doxacore.modelo.Modulo;
import com.doxacore.modelo.Operacion;
import com.doxacore.modelo.Rol;
import com.doxacore.modelo.RolOperacion;
import com.doxacore.modelo.Usuario;
import com.doxacore.modelo.UsuarioRol;

public class UtilStaticMetodos {

	public static String getSHA256(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}
	
	public static void generarDatosInicio(Register reg) {
		
		System.out.println("Creando Datos de Inicio.");
		System.out.println("Creando Usuario Admin");
		Usuario us = new Usuario();
		us.setAccount("admin");
		us.setPassword(getSHA256("123"));
		us.setFullName("Administrador");
		us.setActivo(true);
		us.setEmail("ricardo.gonzalez@doxa.com.py");
		us.setActivo(true);
		us = reg.saveObject(us, "System");
		System.out.println("Se creo el Usuario con el id"+us.getUsuarioid());
		
		System.out.println("Creando Roles");
		System.out.println("Creando Rol Master");
		Rol rolMaster = new Rol();
		rolMaster.setRol("Master");
		rolMaster.setDescripcion("Master");
		rolMaster = reg.saveObject(rolMaster, "System");
		System.out.println("Se creo el Rol Master con el id "+rolMaster.getRol());
		
		System.out.println("Asociando Usuario con Rol");
		UsuarioRol ur = new UsuarioRol();
		ur.setUsuario(us);
		ur.setRol(rolMaster);
		reg.saveObject(ur, "System");
		
		System.out.println("Creando Modulos");
		System.out.println("Creando Modulo Usuario");
		Modulo m1 = new Modulo();
		m1.setModulo("Usuario");
		m1.setDescripcion("Modulo de carga de Usuarios");
		m1.setPath("/doxacore/zul/configuracion/usuario.zul");
		m1.setTitulo("Usuarios");
		m1.setMenu("Configuracion");
		m1.setHabilitado(true);
		m1 = reg.saveObject(m1, "System");
		
		System.out.println("Creando Operaciones del modulo Usuarios");
		Operacion op = new Operacion();
		op.setOperacion("AbrirUsuarios");
		op.setAbreModulo(true);
		op.setDescripcion("Abrir Usuarios");
		op.setModulo(m1);
		op = reg.saveObject(op, "System");
		
		Operacion op2 = new Operacion();
		op2.setOperacion("CrearUsuario");
		op2.setDescripcion("Crear Usuario");
		op2.setModulo(m1);
		op2 = reg.saveObject(op2, "System");
		
		Operacion op3 = new Operacion();
		op3.setOperacion("EditarUsuario");
		op3.setDescripcion("Editar Usuario");
		op3.setModulo(m1);
		op3 = reg.saveObject(op3, "System");
		
		Operacion op4 = new Operacion();
		op4.setOperacion("BorrarUsuario");
		op4.setDescripcion("Borrar Usuario");
		op4.setModulo(m1);
		op4 = reg.saveObject(op4, "System");
		
		Operacion op4x1 = new Operacion();
		op4x1.setOperacion("AgregarUsuarioRol");
		op4x1.setDescripcion("Agregar Usuario Rol");
		op4x1.setModulo(m1);
		op4x1 = reg.saveObject(op4x1, "System");
		
		Operacion op4x2 = new Operacion();
		op4x2.setOperacion("QuitarUsuarioRol");
		op4x2.setDescripcion("Quitar Usuario Rol");
		op4x2.setModulo(m1);
		op4x2 = reg.saveObject(op4x2, "System");
		
		System.out.println("Asociando Operaciones con rol");
		
		RolOperacion ro = new RolOperacion();
		ro.setRol(rolMaster);
		ro.setOperacion(op);
		reg.saveObject(ro, "System");
		
		RolOperacion ro2 = new RolOperacion();
		ro2.setRol(rolMaster);
		ro2.setOperacion(op2);
		reg.saveObject(ro2, "System");

		RolOperacion ro3 = new RolOperacion();
		ro3.setRol(rolMaster);
		ro3.setOperacion(op3);
		reg.saveObject(ro3, "System");
		
		RolOperacion ro4 = new RolOperacion();
		ro4.setRol(rolMaster);
		ro4.setOperacion(op4);
		reg.saveObject(ro4, "System");
		
		RolOperacion ro4x1 = new RolOperacion();
		ro4x1.setRol(rolMaster);
		ro4x1.setOperacion(op4x1);
		reg.saveObject(ro4x1, "System");
		
		RolOperacion ro4x2 = new RolOperacion();
		ro4x2.setRol(rolMaster);
		ro4x2.setOperacion(op4x2);
		reg.saveObject(ro4x2, "System");
		
		
		System.out.println("Creando Modulo Rol");
		Modulo m2 = new Modulo();
		m2.setModulo("Rol");
		m2.setDescripcion("Modulo de carga de Roles");
		m2.setPath("/doxacore/zul/configuracion/rol.zul");
		m2.setTitulo("Roles");
		m2.setMenu("Configuracion");
		m2.setHabilitado(true);
		m2 = reg.saveObject(m2, "System");
		
		System.out.println("Creando Operaciones del modulo Rol");
		Operacion op5 = new Operacion();
		op5.setOperacion("AbrirRoles");
		op5.setDescripcion("Abrir Roles");
		op5.setAbreModulo(true);
		op5.setModulo(m2);
		op5 = reg.saveObject(op5, "System");
		
		Operacion op6 = new Operacion();
		op6.setOperacion("CrearRol");
		op6.setDescripcion("Crear Rol");
		op6.setModulo(m2);
		op6 = reg.saveObject(op6, "System");
		
		Operacion op7 = new Operacion();
		op7.setOperacion("EditarRol");
		op7.setDescripcion("Editar Rol");
		op7.setModulo(m2);
		op7 = reg.saveObject(op7, "System");
		
		Operacion op8 = new Operacion();
		op8.setOperacion("BorrarRol");
		op8.setDescripcion("Borrar Rol");
		op8.setModulo(m2);
		op8 = reg.saveObject(op8, "System");	
		
		Operacion op8x1 = new Operacion();
		op8x1.setOperacion("AgregarRolOperacion");
		op8x1.setDescripcion("Agregar Rol Operacion");
		op8x1.setModulo(m2);
		op8x1 = reg.saveObject(op8x1, "System");
		
		Operacion op8x2 = new Operacion();
		op8x2.setOperacion("QuitarRolOperacion");
		op8x2.setDescripcion("Quitar Rol Operacion");
		op8x2.setModulo(m2);
		op8x2 = reg.saveObject(op8x2, "System");
		
		System.out.println("Asociando Roles con Operaciones");
		RolOperacion ro5 = new RolOperacion();
		ro5.setRol(rolMaster);
		ro5.setOperacion(op5);
		reg.saveObject(ro5, "System");
		
		RolOperacion ro6 = new RolOperacion();
		ro6.setRol(rolMaster);
		ro6.setOperacion(op6);
		reg.saveObject(ro6, "System");

		RolOperacion ro7 = new RolOperacion();
		ro7.setRol(rolMaster);
		ro7.setOperacion(op7);
		reg.saveObject(ro7, "System");
		
		RolOperacion ro8 = new RolOperacion();
		ro8.setRol(rolMaster);
		ro8.setOperacion(op8);
		reg.saveObject(ro8, "System");
		
		RolOperacion ro8x1 = new RolOperacion();
		ro8x1.setRol(rolMaster);
		ro8x1.setOperacion(op8x1);
		reg.saveObject(ro8x1, "System");
		
		RolOperacion ro8x2 = new RolOperacion();
		ro8x2.setRol(rolMaster);
		ro8x2.setOperacion(op8x2);
		reg.saveObject(ro8x2, "System");
		
		
		
		System.out.println("Creando Modulo Modulos");
		Modulo m3 = new Modulo();
		m3.setModulo("Modulo");
		m3.setDescripcion("Modulo de carga de modulo");
		m3.setPath("/doxacore/zul/configuracion/modulo.zul");
		m3.setTitulo("Modulos");
		m3.setMenu("Configuracion");
		m3.setHabilitado(true);
		m3 = reg.saveObject(m3, "System");
		
		System.out.println("Creando Operaciones del modulo Modulo");
		Operacion op9 = new Operacion();
		op9.setOperacion("AbrirModulos");
		op9.setDescripcion("Abrir Modulos");
		op9.setAbreModulo(true);
		op9.setModulo(m3);
		op9 = reg.saveObject(op9, "System");
		
		Operacion op10 = new Operacion();
		op10.setOperacion("CrearModulo");
		op10.setDescripcion("Crear Modulo");
		op10.setModulo(m3);
		op10 = reg.saveObject(op10, "System");
		
		Operacion op11 = new Operacion();
		op11.setOperacion("EditarModulo");
		op11.setDescripcion("Editar Modulo");
		op11.setModulo(m3);
		op11 = reg.saveObject(op11, "System");
		
		Operacion op12 = new Operacion();
		op12.setOperacion("BorrarModulo");
		op12.setDescripcion("Borrar Modulo");
		op12.setModulo(m3);
		op12 = reg.saveObject(op12, "System");
		
		Operacion op12x1 = new Operacion();
		op12x1.setOperacion("CrearOperacion");
		op12x1.setDescripcion("Crear Operacion");
		op12x1.setModulo(m3);
		op12x1 = reg.saveObject(op12x1, "System");
		
		Operacion op12x2 = new Operacion();
		op12x2.setOperacion("BorrarOperacion");
		op12x2.setDescripcion("Borrar Operacion");
		op12x2.setModulo(m3);
		op12x2 = reg.saveObject(op12x2, "System");
		
		Operacion op12x3 = new Operacion();
		op12x3.setOperacion("EditarOperacion");
		op12x3.setDescripcion("Editar Operacion");
		op12x3.setModulo(m3);
		op12x3 = reg.saveObject(op12x3, "System");
		
		System.out.println("Asociando Roles con Operaciones");
		RolOperacion ro9 = new RolOperacion();
		ro9.setRol(rolMaster);
		ro9.setOperacion(op9);
		reg.saveObject(ro9, "System");
		
		RolOperacion ro10 = new RolOperacion();
		ro10.setRol(rolMaster);
		ro10.setOperacion(op10);
		reg.saveObject(ro10, "System");

		RolOperacion ro11 = new RolOperacion();
		ro11.setRol(rolMaster);
		ro11.setOperacion(op11);
		reg.saveObject(ro11, "System");
		
		RolOperacion ro12 = new RolOperacion();
		ro12.setRol(rolMaster);
		ro12.setOperacion(op12);
		reg.saveObject(ro12, "System");
		
		RolOperacion ro12x1 = new RolOperacion();
		ro12x1.setRol(rolMaster);
		ro12x1.setOperacion(op12x1);
		reg.saveObject(ro12x1, "System");
		
		RolOperacion ro12x2 = new RolOperacion();
		ro12x2.setRol(rolMaster);
		ro12x2.setOperacion(op12x2);
		reg.saveObject(ro12x2, "System");
		
		RolOperacion ro12x3 = new RolOperacion();
		ro12x3.setRol(rolMaster);
		ro12x3.setOperacion(op12x3);
		reg.saveObject(ro12x3, "System");
		
		
		
		System.out.println("Creando Modulo Tipotipos");
		Modulo m4 = new Modulo();
		m4.setModulo("Tipotipo");
		m4.setDescripcion("Modulo de carga de Tipotipo");
		m4.setPath("/doxacore/zul/configuracion/tipotipo.zul");
		m4.setTitulo("Tipotipo");
		m4.setMenu("Configuracion");
		m4.setHabilitado(true);
		m4 = reg.saveObject(m4, "System");
		
		System.out.println("Creando Operaciones del modulo Tipotipo");
		Operacion op13 = new Operacion();
		op13.setOperacion("AbrirTipotipos");
		op13.setDescripcion("Abrir Tipotipos");
		op13.setAbreModulo(true);
		op13.setModulo(m4);
		op13 = reg.saveObject(op13, "System");
		
		Operacion op14 = new Operacion();
		op14.setOperacion("CrearTipotipo");
		op14.setDescripcion("Crear Tipotipo");
		op14.setModulo(m4);
		op14 = reg.saveObject(op14, "System");
		
		Operacion op15 = new Operacion();
		op15.setOperacion("EditarTipotipo");
		op15.setDescripcion("Editar Tipotipo");
		op15.setModulo(m4);
		op15 = reg.saveObject(op15, "System");
		
		Operacion op16 = new Operacion();
		op16.setOperacion("BorrarTipotipo");
		op16.setDescripcion("Borrar Tipotipo");
		op16.setModulo(m4);
		op16 = reg.saveObject(op16, "System");
		
		Operacion op16x1 = new Operacion();
		op16x1.setOperacion("CrearTipo");
		op16x1.setDescripcion("Crear Tipo");
		op16x1.setModulo(m4);
		op16x1 = reg.saveObject(op16x1, "System");
		
		Operacion op16x2 = new Operacion();
		op16x2.setOperacion("BorrarTipo");
		op16x2.setDescripcion("Borrar Tipo");
		op16x2.setModulo(m4);
		op16x2 = reg.saveObject(op16x2, "System");
		
		Operacion op16x3 = new Operacion();
		op16x3.setOperacion("EditarTipo");
		op16x3.setDescripcion("Editar Tipo");
		op16x3.setModulo(m4);
		op16x3 = reg.saveObject(op16x3, "System");
		
		
		
		//Agregar operaciones a roles
		System.out.println("Asociando Roles con Operaciones");
		RolOperacion ro13 = new RolOperacion();
		ro13.setRol(rolMaster);
		ro13.setOperacion(op13);
		reg.saveObject(ro13, "System");
		
		RolOperacion ro14 = new RolOperacion();
		ro14.setRol(rolMaster);
		ro14.setOperacion(op14);
		reg.saveObject(ro14, "System");

		RolOperacion ro15 = new RolOperacion();
		ro15.setRol(rolMaster);
		ro15.setOperacion(op15);
		reg.saveObject(ro15, "System");
		
		RolOperacion ro16 = new RolOperacion();
		ro16.setRol(rolMaster);
		ro16.setOperacion(op16);
		reg.saveObject(ro16, "System");
		
		RolOperacion ro16x1 = new RolOperacion();
		ro16x1.setRol(rolMaster);
		ro16x1.setOperacion(op16x1);
		reg.saveObject(ro16x1, "System");
		
		RolOperacion ro16x2 = new RolOperacion();
		ro16x2.setRol(rolMaster);
		ro16x2.setOperacion(op16x2);
		reg.saveObject(ro16x2, "System");
		
		RolOperacion ro16x3 = new RolOperacion();
		ro16x3.setRol(rolMaster);
		ro16x3.setOperacion(op16x3);
		reg.saveObject(ro16x3, "System");
		
		//Modulo Sistema Propiedad
				System.out.println("Creando Modulo Sistema Propiedad");
				Modulo sp = new Modulo();
				sp.setModulo("SistemaPropiedad");
				sp.setDescripcion("Modulo de Sistema Propiedad");
				sp.setPath("/doxacore/zul/configuracion/sistemaPropiedad.zul");
				sp.setTitulo("Sistema Propiedad");
				sp.setMenu("Configuracion");
				sp.setHabilitado(true);
				sp = reg.saveObject(sp, "System");
				
				System.out.println("Creando Operaciones del modulo Tipotipo");
				Operacion op17x1 = new Operacion();
				op17x1.setOperacion("AbrirSistemaPropiedad");
				op17x1.setDescripcion("Abrir SistemaPropiedad");
				op17x1.setAbreModulo(true);
				op17x1.setModulo(sp);
				op17x1 = reg.saveObject(op17x1, "System");
				
				RolOperacion ro17x1 = new RolOperacion();
				ro17x1.setRol(rolMaster);
				ro17x1.setOperacion(op17x1);
				reg.saveObject(ro17x1, "System");
		
		//Modulo Info
		System.out.println("Creando Modulo Info");
		Modulo info = new Modulo();
		info.setModulo("Info");
		info.setDescripcion("Modulo de Informacion");
		info.setPath("/doxacore/zul/configuracion/info.zul");
		info.setTitulo("Info");
		info.setMenu("Configuracion");
		info.setHabilitado(true);
		info = reg.saveObject(info, "System");
		
		System.out.println("Creando Operaciones del modulo Tipotipo");
		Operacion op17 = new Operacion();
		op17.setOperacion("AbrirInfo");
		op17.setDescripcion("Abrir Info");
		op17.setAbreModulo(true);
		op17.setModulo(info);
		op17 = reg.saveObject(op17, "System");
		
		RolOperacion ro17 = new RolOperacion();
		ro17.setRol(rolMaster);
		ro17.setOperacion(op17);
		reg.saveObject(ro17, "System");
		
		
	}

}
