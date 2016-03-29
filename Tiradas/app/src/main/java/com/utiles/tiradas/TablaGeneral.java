package com.utiles.tiradas;

import java.util.ArrayList;
import java.util.List;

import com.utiles.tiradas.EfectosPersonaje;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TablaGeneral extends Activity{
	
	private TableLayout cabeceraGeneralGrupo;
	private TableLayout tablaGeneralGrupo;
	private TextView labelEfectosGrupoTabla;
	private TableRow.LayoutParams layoutM;
	private TableRow.LayoutParams layoutL;
	private TableRow.LayoutParams layoutS;
	private static final float SIZE_G = 110f;
	private static final float SIZE_S = 50f;
	private static final float SIZE_M = 68f;
	AdaptadorBD db;
	private Dialog customDialog = null;
	private Dialog dialogBuffs = null;
	private Dialog dialogAC = null;
	private Dialog dialogAtaque = null;
	private Dialog dialogArma = null;
	private ViewGroup layout;
	private ScrollView scrollView;
	private ScrollView scrollViewTabla;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablageneralgrupo);
		
		// Get the screen's density scale
		float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		int escalaPixelL;
		int escalaPixelM;
		int escalaPixelS;
		escalaPixelL = (int) (SIZE_G * scale + 0.5f);
		escalaPixelS = (int) (SIZE_S * scale + 0.5f);
		escalaPixelM = (int) (SIZE_M * scale + 0.5f);
		
		cabeceraGeneralGrupo = (TableLayout) findViewById(R.id.cabeceraGeneralGrupo);
		scrollViewTabla = (ScrollView) findViewById(R.id.scrollTablaGeneralGrupo);
		tablaGeneralGrupo = (TableLayout) findViewById(R.id.tablaGeneralGrupo);
		layoutL = new TableRow.LayoutParams(escalaPixelL, TableRow.LayoutParams.WRAP_CONTENT);
		layoutS = new TableRow.LayoutParams(escalaPixelS, TableRow.LayoutParams.WRAP_CONTENT);
		layoutM = new TableRow.LayoutParams(escalaPixelM, TableRow.LayoutParams.WRAP_CONTENT);
		labelEfectosGrupoTabla = (TextView) findViewById(R.id.labelEfectosGrupoTabla);
		
		agregarCabecera();
		agregarFilasTabla();
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
	        case R.id.MnuOpc1:
	        	mostrarAgregarEfecto(this.getCurrentFocus());
	            return true;
	        case R.id.MnuOpc2:
	        	mostrarEliminarEfecto(this.getCurrentFocus());
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_tabla, menu);
		return true;
	}
	
	private void agregarCabecera(){
		
		TableRow fila;
		TextView textoNombrePersonaje;
		TextView textoACPersonaje;
		TextView textoMeleePersonaje;
		TextView textoDadoDmg;
		TextView textoDmgPersonaje;
		TextView textoSaveFort;
		TextView textoSaveRef;
		TextView textoSaveWill;
		
		fila = new TableRow(getBaseContext());
		textoNombrePersonaje = new TextView(getBaseContext());
		textoACPersonaje = new TextView(getBaseContext());
		textoMeleePersonaje = new TextView(getBaseContext());
		textoDmgPersonaje = new TextView(getBaseContext());
		textoSaveFort = new TextView(getBaseContext());
		textoSaveRef = new TextView(getBaseContext());
		textoSaveWill = new TextView(getBaseContext());
		textoDadoDmg = new TextView(getBaseContext());
		
		textoNombrePersonaje.setText("NOMBRE");
		textoNombrePersonaje.setGravity(Gravity.CENTER_VERTICAL);
		textoNombrePersonaje.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoNombrePersonaje.setTextColor(Color.parseColor("#000000"));
		textoNombrePersonaje.setTypeface(null, Typeface.BOLD);
		textoNombrePersonaje.setLayoutParams(layoutL);
		
		textoACPersonaje.setText("AC");
		textoACPersonaje.setGravity(Gravity.CENTER);
		textoACPersonaje.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoACPersonaje.setTextColor(Color.parseColor("#000000"));
		textoACPersonaje.setTypeface(null, Typeface.BOLD);
		textoACPersonaje.setLayoutParams(layoutS);
		
		textoMeleePersonaje.setText("ATK");
		textoMeleePersonaje.setGravity(Gravity.CENTER);
		textoMeleePersonaje.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoMeleePersonaje.setTextColor(Color.parseColor("#000000"));
		textoMeleePersonaje.setTypeface(null, Typeface.BOLD);
		textoMeleePersonaje.setLayoutParams(layoutS);
		
		textoDadoDmg.setText("DAÑO");
		textoDadoDmg.setGravity(Gravity.CENTER);
		textoDadoDmg.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoDadoDmg.setTextColor(Color.parseColor("#000000"));
		textoDadoDmg.setTypeface(null, Typeface.BOLD);
		textoDadoDmg.setLayoutParams(layoutL);
		
		textoDmgPersonaje.setText("DMG");
		textoDmgPersonaje.setGravity(Gravity.CENTER);
		textoDmgPersonaje.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoDmgPersonaje.setTextColor(Color.parseColor("#000000"));
		textoDmgPersonaje.setTypeface(null, Typeface.BOLD);
		textoDmgPersonaje.setLayoutParams(layoutM);
		
		textoSaveFort.setText("SVFOR");
		textoSaveFort.setGravity(Gravity.CENTER);
		textoSaveFort.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoSaveFort.setTextColor(Color.parseColor("#000000"));
		textoSaveFort.setTypeface(null, Typeface.BOLD);
		textoSaveFort.setLayoutParams(layoutM);
		
		textoSaveRef.setText("SVREF");
		textoSaveRef.setGravity(Gravity.CENTER);
		textoSaveRef.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoSaveRef.setTextColor(Color.parseColor("#000000"));
		textoSaveRef.setTypeface(null, Typeface.BOLD);
		textoSaveRef.setLayoutParams(layoutM);
		
		textoSaveWill.setText("SVVOL");
		textoSaveWill.setGravity(Gravity.CENTER);
		textoSaveWill.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoSaveWill.setTextColor(Color.parseColor("#000000"));
		textoSaveWill.setTypeface(null, Typeface.BOLD);
		textoSaveWill.setLayoutParams(layoutM);
		
		fila.addView(textoNombrePersonaje);
		fila.addView(textoACPersonaje);
		fila.addView(textoMeleePersonaje);
		fila.addView(textoDadoDmg);
		fila.addView(textoDmgPersonaje);
		fila.addView(textoSaveFort);
		fila.addView(textoSaveRef);
		fila.addView(textoSaveWill);

		cabeceraGeneralGrupo.addView(fila);
		
	}
	
	private void agregarFilasTabla(){
		TableRow fila;
		TextView textoNombrePersonaje;
		TextView textoACPersonaje;
		TextView textoMeleePersonaje;
		TextView textoDadoDmg;
		TextView textoDmgPersonaje;
		TextView textoSaveFort;
		TextView textoSaveRef;
		TextView textoSaveWill;
    	
		db = new AdaptadorBD(getBaseContext());
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT idPersonaje, nombre FROM PERSONAJES;",null);
			if (c.moveToFirst()) {
				do {
					fila = new TableRow(getBaseContext());
		    		
		    		textoNombrePersonaje = new TextView(this);
		    		textoACPersonaje = new TextView(this);
		    		textoMeleePersonaje = new TextView(this);
		    		textoDadoDmg = new TextView(this);
		    		textoDmgPersonaje = new TextView(this);
		    		textoSaveFort = new TextView(this);
		    		textoSaveRef = new TextView(this);
		    		textoSaveWill = new TextView(this);
		    		
		    		int armor = 10;
		    		int atkBase = 0;
		    		int saveF = 0;
		    		int saveR = 0;
		    		int saveV = 0;
		    		String dadosDmg = "";
		    		String tipoArma = "";
		    		int dmg = 0;
		    		int modStatDmg = 0;
		    		int idTamaño = 0;
		    		int fuerzaPersonaje = 0;
		    		int destrezaPersonaje = 0;
		    		int consPersonaje = 0;
		    		int intPersonaje = 0;
		    		int sabPersonaje = 0;
		    		int carPersonaje = 0;

		    		//Conseguir id, nombre y clase del personaje
					final int idPersonaje = c.getInt(0);
					final String nombrePersonaje = c.getString(1);
					List<Integer> clases = new ArrayList<Integer>();
					Cursor clase = db.consulta("SELECT idClase FROM PERSONAJES_CLASES WHERE idPersonaje = " + idPersonaje + ";", null);
					if (clase.moveToFirst()){
						do {
							clases.add(clase.getInt(0));
						} while (clase.moveToNext());
					}
					clase.close();
					
					/* EFECTOS DEL PERSONAJE */
		    		int AtkBuffs = 0;
		    		int ACBuffs = 0;
		    		int DmgBuffs = 0;
		    		//Ver a que afecta el efecto y aplicarlo a los modificadores correspondientes
		    		List<EfectosPersonaje> lista = new ArrayList<EfectosPersonaje>();
					Cursor cc = db.consulta(
							"SELECT idStat, SUM(Valor) FROM ( " + 
								"SELECT DISTINCT b.valor, a.idStat, a.idTipo FROM " +  
								"(SELECT idPersonaje, a.idEfecto, a.idTipo, a.IdStat, a.valor " + 
								"FROM EFECTOS_STATS a " +  
								"INNER JOIN PERSONAJES_EFECTOS b ON b.idEfecto = a.idEfecto " + 
								"WHERE idPersonaje = " + idPersonaje + " AND afectado = 1 ) a " + 
								"INNER JOIN " +  
								"(SELECT MAX(a.valor) as valor, a.idTipo, a.idStat, a.idTipo " + 
								"FROM EFECTOS_STATS a INNER JOIN " +  
								"(SELECT idPersonaje, a.idEfecto, a.idTipo, a.IdStat, a.valor " + 
								"FROM EFECTOS_STATS a  INNER JOIN PERSONAJES_EFECTOS b ON b.idEfecto = a.idEfecto " + 
								"WHERE idPersonaje = " + idPersonaje + " AND afectado = 1 " +  
								"AND idtipo not in(6,3,19) ) b ON a.idEfecto= b.idEfecto " + 
								"GROUP BY a.idStat,a.idTipo )b ON a.idTipo = b.idTipo " + 
								"AND a.idStat = b.idStat AND a.valor = b.valor " + 
								"union all " + 
								"SELECT SUM(a.valor) as valor, a.IdStat, a.idTipo " + 
								"FROM EFECTOS_STATS a " +  
								"INNER JOIN PERSONAJES_EFECTOS b ON b.idEfecto = a.idEfecto " + 
								"WHERE idPersonaje = " + idPersonaje + " AND afectado = 1 AND idtipo in(6,13,19) " + 
								"GROUP BY idstat, a.idTipo) c GROUP BY idstat", null);
					if (cc.moveToFirst()){
						do {
							lista.add(new EfectosPersonaje(cc.getInt(0), cc.getInt(1)));									
						} while (cc.moveToNext());
					}
					cc.close();
					
					for (int i=0; i < lista.size(); i++){
						EfectosPersonaje ef = lista.get(i);
						int idStat = ef.getStat();
						int valor = ef.getValor();
						switch (idStat){
							case 1:
								//Fuerza
								fuerzaPersonaje += valor;
								break;
							case 2:
								//Destreza
								destrezaPersonaje += valor;
								break;
							case 3:
								//Constitucion
								consPersonaje += valor;
								break;
							case 4:
								//Sabiduria
								sabPersonaje += valor;
								break;
							case 5:
								//Inteligencia
								intPersonaje += valor;
								break;
							case 6:
								//Carisma
								carPersonaje += valor;
								break;
							case 7:
								//AC
								ACBuffs += valor;
								break;
							case 8:
								//meleeAtk
								AtkBuffs += valor;
								break;
							case 10:
								//saveF
								saveF += valor;
								break;
							case 11:
								//saveR
								saveR += valor;
								break;
							case 12:
								//saveV
								saveV += valor;
								break;
							case 22:
								//DMG
								DmgBuffs += valor;
								break;
							default:
							
						}
					}
					
					/* MODIFICADORES DE ESTADISTICAS */
					int DesMod = 0;
					//Buscar puntuacion de destreza del personaje
					Cursor c1 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Destreza' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c1.moveToFirst();
					destrezaPersonaje += c1.getInt(0);
					c1.close();
					//Obtener modificador de destreza
					Cursor c2 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + destrezaPersonaje + ";", null);
					c2.moveToFirst();
					DesMod = c2.getInt(0);
					c2.close();
					final int modDestreza = DesMod;
					
					//Buscar puntuacion de fuerza del personaje
					int StrMod = 0;
					Cursor c4 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Fuerza' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c4.moveToFirst();
					fuerzaPersonaje += c4.getInt(0);
					c4.close();
					//Obtener modificador de fuerza
					Cursor c5 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + fuerzaPersonaje + ";", null);
					c5.moveToFirst();
					StrMod = c5.getInt(0);
					c5.close();
					final int modFuerza = StrMod;
					
					//Buscar puntuacion de constitucion del personaje
					int ConMod = 0;
					Cursor c11 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Constitucion' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c11.moveToFirst();
					consPersonaje += c11.getInt(0);
					c11.close();
					//Obtener modificador de constitucion
					Cursor c12 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + consPersonaje + ";", null);
					c12.moveToFirst();
					ConMod = c12.getInt(0);
					c12.close(); 
					final int modCon = ConMod;
					
					//Buscar puntuacion de inteligencia del personaje
					
					Cursor cIntBase = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Inteligencia' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					cIntBase.moveToFirst();
					intPersonaje += cIntBase.getInt(0);
					cIntBase.close();
					//Obtener modificador de inteligencia
					Cursor cModInt = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + intPersonaje + ";", null);
					cModInt.moveToFirst();
					
					cModInt.close();
					
					//Buscar puntuacion de sabiduria del personaje
					int SabMod = 0;
					Cursor c6 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Sabiduria' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c6.moveToFirst();
					sabPersonaje += c6.getInt(0);
					c6.close();
					//Obtener modificador de sabiduria
					Cursor c7 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + sabPersonaje + ";", null);
					c7.moveToFirst();
					SabMod = c7.getInt(0);
					c7.close();
					final int modSab = SabMod;
					
					//Buscar puntuacion de carisma del personaje
					int CarMod = 0;
					Cursor cCarBase = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Carisma' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					cCarBase.moveToFirst();
					carPersonaje += cCarBase.getInt(0);
					cCarBase.close();
					//Obtener modificador de carisma
					Cursor cCarMod = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + carPersonaje + ";", null);
					cCarMod.moveToFirst();
					CarMod = cCarMod.getInt(0);
					cCarMod.close();
					final int modCar = CarMod;
					
					/* MODIFICADORES DE TAMA�O */
					
					//Comprobar tama�o del personaje
		    		Cursor c10 = db.consulta("SELECT idTamano FROM PERSONAJES WHERE idPersonaje = " + idPersonaje + ";", null);
		    		c10.moveToFirst();
		    		idTamaño = c10.getInt(0);
		    		c10.close();
		    		final int idTamano = idTamaño;
		    		String dados= "";
		    		switch (idTamaño){
			    		case 1:
			    			dados = "dmF";
			    			break;
			    		case 2:
			    			dados = "dmD";
			    			break;
			    		case 3:
			    			dados = "dmT";
			    			break;
			    		case 4:
			    			dados = "dmS";
			    			break;
			    		case 5:
			    			dados = "dmM";
			    			break;
			    		case 6:
			    			dados = "dmL";
			    			break;
			    		case 7:
			    			dados = "dmH";
			    			break;
			    		case 8:
			    			dados = "dmG";
			    			break;
			    		case 9:
			    			dados = "dmC";
			    			break;
		    		}
		    		Cursor cTamaño = db.consulta("SELECT acBonus, atkBonus FROM TAMANOS WHERE idTamano = " + idTamaño + ";", null);
		    		cTamaño.moveToFirst();
		    		final int modAcTamaño = cTamaño.getInt(0);
		    		int modAtkTamaño = cTamaño.getInt(1);
		    		cTamaño.close();
					
		    		/* ATAQUE BASE Y SAVES SEGUN CLASE */
		    		
		    		//Calcular el ataque (Ataque base + fuerza + efectos)
		    		
		    		//Buscar clase del personaje y obtener ataque base y saves en funcion del nivel/es del personaje
		    		//Primero obtener clases y niveles
		    		Cursor c3 = db.consulta("SELECT idClase, nivel FROM PERSONAJES_CLASES WHERE idPersonaje = " + idPersonaje + ";", null);
		    		List <Integer> listaAtk = new ArrayList<Integer>();
		    		List <Integer> listaSaveF = new ArrayList<Integer>();
		    		List <Integer> listaSaveR = new ArrayList<Integer>();
		    		List <Integer> listaSaveV = new ArrayList<Integer>();
		    		if (c3.moveToFirst()){
		    			do{
		    				int idClase = c3.getInt(0);
		    				int nivel = c3.getInt(1);
		    				//Buscar ataque base y saves de la clase y nivel obtenidos
		    				Cursor c33 = db.consulta(
		    						"SELECT saveFort, saveRef, saveWill, baseAtk " + 
		    						"FROM CLASES_ATK_SAVES WHERE idClase = " + idClase + " AND nivel = " + nivel + ";", null);
		    				//A�adir los datos obtenidos a las arraylists
		    				if (c33.moveToFirst()){
		    					do {
		    						listaAtk.add(c33.getInt(3));
						    		listaSaveF.add(c33.getInt(0));
						    		listaSaveR.add(c33.getInt(1));
						    		listaSaveV.add(c33.getInt(2));		    						
		    					} while (c33.moveToNext());
		    				}
		    				c33.close();
		    			} while (c3.moveToNext());
		    		}
		    		c3.close();
		    		//Calcular su armadura (10 + Destreza + escudo + Tamaño + efectos) Armadura = efecto (mas abajo)
					int idEscudo = 0;
					int escudoAC = 0;
					Cursor c8 = db.consulta("SELECT offEscudo FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
					if (c8.moveToFirst()){
						do {
							idEscudo = c8.getInt(0);
							if (idEscudo > 0){
								Cursor c82 = db.consulta("SELECT bonoEscudo from ARMADURAS WHERE idArmadura = " + idEscudo + ";", null);
								c82.moveToFirst();
								escudoAC = c82.getInt(0);
								c82.close();
							}
						} while (c8.moveToNext());
					}
					c8.close();
					final int escudo = idEscudo;
					final int armorEscudo = escudoAC;
					
		    		/* DADOS DE DAÑO DEL ARMA DEL PERSONAJE */
		    		
		    		//Comprobar si el personaje lleva arma equipada en las dos manos
		    		int idArma = 0;
		    		int idArmaOff = 0;
		    		Cursor c9 = db.consulta("SELECT mainhand, offArma FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
					if (c9.moveToFirst()){
						do {
							idArma = c9.getInt(0);
							idArmaOff = c9.getInt(1);
							if (idArma > 0){
								Cursor c91 = db.consulta("SELECT " + dados + ", tipoArma from ARMAS WHERE idArma = " + idArma + ";", null);
								c91.moveToFirst();
								dadosDmg += c91.getString(0);
								tipoArma = c91.getString(1);
								c91.close();
							}
							if (idArmaOff > 0){
								Cursor c92 = db.consulta("SELECT " + dados + " from ARMAS WHERE idArma = " + idArmaOff + ";", null);
								c92.moveToFirst();
								dadosDmg += " / " + c92.getString(0);
								c92.close();
							}
							if (idArma == 0 && idArmaOff == 0){
								dadosDmg = "---";
							}
						} while (c9.moveToNext());
					}
					c9.close();
					final int arma = idArma;
					final int offhand = idArmaOff;
		    		
		    		//Sumar el total de ataque base de los datos metidos en las listas
		    		for (int i=0; i < listaAtk.size(); i++){
		    			atkBase += listaAtk.get(i);
		    		}
		    		final int baseAtk = atkBase;
		    		
		    		//Sumar el total de saves
		    		for (int i=0; i < listaSaveF.size(); i++){
		    			saveF += listaSaveF.get(i);
		    		}
		    		for (int i=0; i < listaSaveR.size(); i++){
		    			saveR += listaSaveR.get(i);
		    		}
		    		for (int i=0; i < listaSaveV.size(); i++){
		    			saveV += listaSaveV.get(i);
		    		}
		    		
					boolean finesse = false;
					Cursor cDote1 = db.consulta(
		    				"SELECT a.nombre " + 
		    				"FROM EFECTOS a INNER JOIN PERSONAJES_EFECTOS b ON  a.idEfecto = b.idEfecto " + 
		    				"AND b.idPersonaje = " + idPersonaje + ";", null);
		    		if (cDote1.moveToFirst()){
		    			do {
		    				String efecto = cDote1.getString(0);
		    				if (efecto.equalsIgnoreCase("Sutileza con las armas")){
		    					finesse = true;
		    				}
		    			} while (cDote1.moveToNext());
		    		}
		    		final boolean sutileza = finesse;
		    		boolean dervish = false;
					Cursor cDote2 = db.consulta(
		    				"SELECT a.nombre " + 
		    				"FROM EFECTOS a INNER JOIN PERSONAJES_EFECTOS b ON  a.idEfecto = b.idEfecto " + 
		    				"AND b.idPersonaje = " + idPersonaje + ";", null);
		    		if (cDote2.moveToFirst()){
		    			do {
		    				String efecto = cDote2.getString(0);
		    				if (efecto.equalsIgnoreCase("Dervish Dance")){
		    					//Hallar id del arma "Cimitarra"
		    					int idCimitarra = 0;
		    					Cursor cArma = db.consulta("SELECT idArma FROM ARMAS WHERE nombre = 'Cimitarra';", null);
		    					if (cArma.moveToFirst()){
		    						do {
		    							idCimitarra = cArma.getInt(0);
		    						} while (cArma.moveToNext());
		    					}
		    					if (idCimitarra != 0){
		    						if (idArma == idCimitarra && idEscudo == 0 && idArmaOff == 0){
		    							dervish = true;
		    						}
		    					}
		    				}
		    			} while (cDote2.moveToNext());
		    		}
		    		
		    		int modStatAtk = modFuerza;
		    		
					int dexBonusMax = 0;
		    		int idArmadura = 0;
		    		Cursor getArmor = db.consulta(
		    				"SELECT a.maxDesBonus, b.armadura FROM ARMADURAS a, PERSONAJES_EQUIPO b " +  
		    				"WHERE a.idArmadura = b.armadura AND b.idpersonaje = " + idPersonaje + ";", null);
		    		if (getArmor.moveToFirst()){
		    			do {
		    				dexBonusMax = getArmor.getInt(0);
		    				idArmadura = getArmor.getInt(1);
		    			} while (getArmor.moveToNext());
		    		}
		    		
		    		
		    		saveF += modCon;
		    		saveR += modDestreza;
		    		saveV += modSab;
		    		
		    		//Calcular entrenamientos con arma y entrenamientos con armadura de la clase warrior
		    		for (int i=0; i < clases.size(); i++){
		    			int idClase = clases.get(i);
		    			String nombre = "";
		    			Cursor caca = db.consulta("SELECT nombre FROM CLASES WHERE idClase = " + idClase + ";", null);
		    			if (caca.moveToFirst()){
		    				do {
		    					nombre = caca.getString(0);
		    				} while (caca.moveToNext());
		    			}
		    			caca.close();
		    			if (nombre.equalsIgnoreCase("luchador") || nombre.equalsIgnoreCase("guerrero") || 
		    					nombre.equalsIgnoreCase("warrior") || nombre.equalsIgnoreCase("fighter")){
		    				int nivelClase = 0;
		    				Cursor nivel = db.consulta(
		    						"SELECT nivel FROM PERSONAJES_CLASES WHERE idClase = " + idClase + " " + 
		    						"AND idPersonaje = " + idPersonaje + ";", null);
		    				if (nivel.moveToFirst()){
		    					do {
		    						nivelClase = nivel.getInt(0);
		    					} while (nivel.moveToNext());
		    				}
		    				nivel.close();
		    				//Entrenamiento con armaduras
		    				if (nivelClase >= 15){
		    					dexBonusMax +=4;
		    				} else if (nivelClase >= 11){
		    					dexBonusMax +=3;
		    				} else if (nivelClase >= 7){
		    					dexBonusMax +=2;
		    				} else if(nivelClase >=3){
		    					dexBonusMax +=1;
		    				}
		    				//Entrenamiento con armas
		    				if (nivelClase >= 17){
		    					AtkBuffs += 4;
		    					dmg += 4;
		    				} else if (nivelClase >= 13){
		    					AtkBuffs +=3;
		    					dmg += 3;
		    				} else if (nivelClase >= 9){
		    					AtkBuffs +=2;
		    					dmg += 2;
		    				} else if(nivelClase >= 5){
		    					AtkBuffs +=1;
		    					dmg += 1;
		    				}
		    			} else if (nombre.equalsIgnoreCase("paladin")) {
		    				int nivelClase = 0;
		    				Cursor nivel = db.consulta(
		    						"SELECT nivel FROM PERSONAJES_CLASES WHERE idClase = " + idClase + " " + 
		    						"AND idPersonaje = " + idPersonaje + ";", null);
		    				if (nivel.moveToFirst()){
		    					do {
		    						nivelClase = nivel.getInt(0);
		    					} while (nivel.moveToNext());
		    				}
		    				nivel.close();
		    				//Suma de modificador de carisma a las salvaciones si es mayos de nivel 2 de paladin
		    				if (nivelClase >=2){
		    					saveF += modCar;
		    		    		saveR += modCar;
		    		    		saveV += modCar;
		    				}
		    			}
		    		}
		    		
		    		final int buffAC = ACBuffs;
		    		final int buffAtk = AtkBuffs;
		    		final int buffDmg = DmgBuffs;
		    		
		    		final int maxDesBonus = dexBonusMax;
		    		
		    		int bonoDesArmadura;
		    		if (modDestreza > maxDesBonus && idArmadura > 0){
		    			bonoDesArmadura = maxDesBonus;
		    		} else {
		    			bonoDesArmadura = modDestreza;
		    		}
		    		armor = armor + bonoDesArmadura + modAcTamaño + buffAC;
		    		
		    		//Calcular el daño (mod fuerza + efectos)
					modStatDmg += modFuerza;
					if (dervish == true){modStatDmg = modDestreza;}
					if (tipoArma.equalsIgnoreCase("2")){ 
		    			modStatDmg += (modFuerza/2); 
		    		} else if (tipoArma.equalsIgnoreCase("R")){
		    			modStatDmg = 0;
		    			modStatAtk = modDestreza;
		    		}
					if (finesse == true){modStatAtk = modDestreza;}
		    		String dañoOff = "";
		    		if (idArmaOff > 0){
		    			dañoOff = " / " + Integer.toString(dmg + (modStatDmg/2) + buffDmg);
		    		}
		    		dmg += modStatDmg + buffDmg;
		    		atkBase = atkBase + modStatAtk + modAtkTamaño + buffAtk;
		    		
		    		String armadura = Integer.toString(armor);
		    		String ataque = Integer.toString(atkBase);
		    		String daño = Integer.toString(dmg) + dañoOff;
		    		
		    		
		    		//Datos celda nombre
					textoNombrePersonaje.setText(nombrePersonaje);
		    		textoNombrePersonaje.setGravity(Gravity.CENTER_VERTICAL);
		    		textoNombrePersonaje.setBackgroundResource(R.drawable.tabla_celda);
		    		textoNombrePersonaje.setLayoutParams(layoutL);
		    		textoNombrePersonaje.setTag("NOMBRE");
		    		textoNombrePersonaje.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							mostrarPersonajeTabla(v);
						}
					});
		    		//Datos celda armadura
		    		textoACPersonaje.setText(armadura);
		    		textoACPersonaje.setGravity(Gravity.CENTER);
		    		textoACPersonaje.setBackgroundResource(R.drawable.tabla_celda);
		    		textoACPersonaje.setLayoutParams(layoutS);
		    		textoACPersonaje.setTag("ARMADURA");
		    		textoACPersonaje.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							mostrarDesgloseArmadura(idPersonaje, modDestreza, maxDesBonus, modAcTamaño, armorEscudo, buffAC);
						}
					});
		    		//Datos celda ATK
		    		textoMeleePersonaje.setText(ataque);
		    		textoMeleePersonaje.setGravity(Gravity.CENTER);
		    		textoMeleePersonaje.setBackgroundResource(R.drawable.tabla_celda);
		    		textoMeleePersonaje.setLayoutParams(layoutS);
		    		textoMeleePersonaje.setTag("ATAQUE");
		    		textoMeleePersonaje.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							mostrarDesgloseAtaque(idPersonaje, baseAtk, sutileza, modDestreza, modFuerza, idTamano, buffAtk, buffAC);
						}
					});
		    		//Datos celda dados dmg
		    		textoDadoDmg.setText(dadosDmg);
		    		textoDadoDmg.setGravity(Gravity.CENTER);
		    		textoDadoDmg.setBackgroundResource(R.drawable.tabla_celda);
		    		textoDadoDmg.setLayoutParams(layoutL);
		    		textoDadoDmg.setTag("DADOSDMG");
		    		textoDadoDmg.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							mostrarDesgloseArma(nombrePersonaje, idPersonaje, idTamano, arma, offhand, escudo);
						}
					});
		    		//Datos celda Damage
		    		textoDmgPersonaje.setText(daño);
		    		textoDmgPersonaje.setGravity(Gravity.CENTER);
		    		textoDmgPersonaje.setBackgroundResource(R.drawable.tabla_celda);
		    		textoDmgPersonaje.setLayoutParams(layoutM);
		    		textoDmgPersonaje.setTag("DAÑO");
		    		//Datos celda Save FORT
		    		textoSaveFort.setText(Integer.toString(saveF));
		    		textoSaveFort.setGravity(Gravity.CENTER);
		    		textoSaveFort.setBackgroundResource(R.drawable.tabla_celda);
		    		textoSaveFort.setLayoutParams(layoutM);
		    		textoSaveFort.setTag("SVF");
		    		//Datos celda Save REF
		    		textoSaveRef.setText(Integer.toString(saveR));
		    		textoSaveRef.setGravity(Gravity.CENTER);
		    		textoSaveRef.setBackgroundResource(R.drawable.tabla_celda);
		    		textoSaveRef.setLayoutParams(layoutM);
		    		textoSaveRef.setTag("SVR");
		    		//Datos celda Save VOL
		    		textoSaveWill.setText(Integer.toString(saveV));;
		    		textoSaveWill.setGravity(Gravity.CENTER);
		    		textoSaveWill.setBackgroundResource(R.drawable.tabla_celda);
		    		textoSaveWill.setLayoutParams(layoutM);
		    		textoSaveWill.setTag("SVV");
		    		
		    		fila.addView(textoNombrePersonaje);
		    		fila.addView(textoACPersonaje);
		    		fila.addView(textoMeleePersonaje);
		    		fila.addView(textoDadoDmg);
		    		fila.addView(textoDmgPersonaje);
		    		fila.addView(textoSaveFort);
		    		fila.addView(textoSaveRef);
		    		fila.addView(textoSaveWill);
		    		
//		    		fila.setOnClickListener(new View.OnClickListener() {
//		    			
//		    			@Override
//		    			public void onClick(View v) {
//		    				String tag = v.getTag().toString();
//		    				if (tag.equalsIgnoreCase("armadura")){
//		    					Toast.makeText(getBaseContext(), "Armadura!", Toast.LENGTH_SHORT).show();
//		    				} else if (tag.equalsIgnoreCase("ataque")){
//		    					Toast.makeText(getBaseContext(), "Ataque!", Toast.LENGTH_SHORT).show();
//		    				} else if (tag.equalsIgnoreCase("dadosdmg")){
//		    					Toast.makeText(getBaseContext(), "Arma!", Toast.LENGTH_SHORT).show();
//		    				} else if (tag.equalsIgnoreCase("daño")){
//		    					Toast.makeText(getBaseContext(), "Daño!", Toast.LENGTH_SHORT).show();
//		    				} else {
//		    					mostrarPersonajeTabla(v);
//		    				}
//		    			}
//		    		});
		    		
		    		tablaGeneralGrupo.addView(fila);
		    		
		    		scrollViewTabla.post(new Runnable() { 
		                public void run() { 
		                	scrollViewTabla.fullScroll(ScrollView.FOCUS_DOWN);
		                } 
		    		});
				} while (c.moveToNext());
				LabelBuffs();
			}
			
			db.cerrar();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
	}

	@SuppressLint("DefaultLocale")
	private void mostrarPersonajeTabla (View view) {
		// con este tema personalizado evitamos los bordes por defecto
		dialogBuffs = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el t�tulo por defecto
		dialogBuffs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
		dialogBuffs.setCancelable(false);
        //establecemos el contenido de nuestro dialog
		dialogBuffs.setContentView(R.layout.dialogpersonajetabla);
		
		layout = (ViewGroup) dialogBuffs.findViewById(R.id.layoutCosasTabla); 
		scrollView = (ScrollView) dialogBuffs.findViewById(R.id.scrollEfectosPersonajeTabla);
        
		TextView txtNombre = (TextView) view;
		
		//Poner nombre del personaje seleccionado en el dialog
        TextView nombrePersonaje = (TextView) dialogBuffs.findViewById(R.id.labelNombrePersonajeTabla);
        nombrePersonaje.setText(txtNombre.getText().toString().toUpperCase());
        final String personaje = txtNombre.getText().toString();
        
        //Rellenar spinner de efectos con todos los efectos de la tabla
        final Spinner desplegableEfectosPersonajeTabla = (Spinner) dialogBuffs.findViewById(R.id.spinnerEfectosPersonajeTabla); 
        db = new AdaptadorBD(getBaseContext());
        
        List<String> lista = new ArrayList<String>();
		List<String> listaF = new ArrayList<String>();
		lista.add("-----------------");
		listaF.add("-------FAV-------");
		// Obtener efectos favoritos
		try {
			db.abrir();
			Cursor cF = db.consulta("SELECT nombre FROM FAV_EFECTOS ORDER BY NOMBRE;", null);
			if (cF.moveToFirst()){
				do {
					listaF.add(cF.getString(0));
				} while (cF.moveToNext());
			}
			cF.close();
			Cursor c = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;",null);
			if (c.moveToFirst()) {
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}
			db.cerrar();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		listaF.addAll(lista);
		//Poner lista de efectos en desplegable de efectos
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaF);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		desplegableEfectosPersonajeTabla.setAdapter(adapter);
        
		desplegableEfectosPersonajeTabla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				String efecto = parent.getSelectedItem().toString();
				if (efecto.equalsIgnoreCase("-----------------")){return;}
				if (efecto.equalsIgnoreCase("-------FAV-------")){return;}
				if (parent.getSelectedItemId() == 0){return;}
				CrearCosa(efecto);
				CrearFavorito(efecto);
				List<String> lista = new ArrayList<String>();
				List<String> listaF = new ArrayList<String>();
				lista.add("-----------------");
				listaF.add("-------FAV-------");
				// Obtener efectos favoritos
				try {
					db.abrir();
					Cursor cF = db.consulta("SELECT nombre FROM FAV_EFECTOS ORDER BY NOMBRE;", null);
					if (cF.moveToFirst()){
						do {
							listaF.add(cF.getString(0));
						} while (cF.moveToNext());
					}
					cF.close();
					Cursor c = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;",null);
					if (c.moveToFirst()) {
						do {
							lista.add(c.getString(0));
						} while (c.moveToNext());
					}
					db.cerrar();
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}
				listaF.addAll(lista);
				//Poner lista de efectos en desplegable de efectos
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
				        android.R.layout.simple_spinner_item, listaF);
				// Specify the layout to use when the list of choices appears
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				desplegableEfectosPersonajeTabla.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				return;
			}
		});
		
		//Mostrar efectos del personaje
		try {
			layout.removeAllViews();
			db.abrir();
			Cursor c = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';",null);
			c.moveToFirst();
			int idPersonaje = c.getInt(0);
			c.close();
			Cursor c1 = db.consulta("SELECT idEfecto FROM PERSONAJES_EFECTOS WHERE idPersonaje = " + idPersonaje + " AND afectado = 1;", null);
			if (c1.moveToFirst()) {
				do {
					int idEfecto = c1.getInt(0);
					Cursor c11 = db.consulta("SELECT nombre FROM EFECTOS WHERE idEfecto = " + idEfecto + ";", null);
					c11.moveToFirst();
					String efecto = c11.getString(0);
					c11.close();
					CrearCosa(efecto);
				} while (c1.moveToNext());
			}
			db.cerrar();
			c1.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		
		Button btnAceptarEfectosPersonajeTabla = (Button) dialogBuffs.findViewById(R.id.btnAceptarEfectosPersonajeTabla);
		btnAceptarEfectosPersonajeTabla.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (EfectoRepetido()){
					return;
				} else {
					try {
						db.abrir();
						Cursor c = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';",null);
						c.moveToFirst();
						int idPersonaje = c.getInt(0);
						c.close();
						db.begin(); 
						db.insertar("DELETE FROM PERSONAJES_EFECTOS WHERE idPersonaje = " + idPersonaje + ";");
						//Recorrer los childs del layout parent 
						int count = layout.getChildCount();
						for (int i=0; i < count; i++){
							//Recoger de cada child la clase y el nivel
							RelativeLayout cosas = (RelativeLayout) layout.getChildAt(i);
							TextView txtNombreEfecto = (TextView) cosas.findViewById(R.id.txtEfectoPersonajeTabla);
							String efecto = txtNombreEfecto.getText().toString();
							//Buscar id del efecto en la base de datos
							int idEfecto = 0;
							Cursor cc = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';",null);
							cc.moveToFirst();
							idEfecto = cc.getInt(0);
							cc.close();
							//Insertar idEfecto, afectado en la base de datos
							db.insertar(
									"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
									"(" + idPersonaje + ", " + idEfecto + ", 1);");
						}
						db.commit();
					} catch (Exception e) {
						e.printStackTrace();
						db.rollback();
					} finally {
						db.rollback();
						db.cerrar();
					}
					dialogBuffs.dismiss();
					tablaGeneralGrupo.removeAllViews();
					agregarFilasTabla();
				}
			}
		});
		Button btnCancelarEfectosPersonajeTabla = (Button) dialogBuffs.findViewById(R.id.btnCancelarEfectosPersonajeTabla);
		btnCancelarEfectosPersonajeTabla.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogBuffs.dismiss();
			}
		});
		dialogBuffs.show();
		
	}
	
	private void CrearCosa(String texto){
		TextView txtEfectoPersonajeTabla;
		Button btnQuitarLinea;
		LayoutInflater inflater = LayoutInflater.from(getBaseContext());
		int id = R.layout.layout_cosas_tabla;
		RelativeLayout setCosasPersonajeTabla = (RelativeLayout) inflater.inflate(id, null, false);
		layout.addView(setCosasPersonajeTabla);
		txtEfectoPersonajeTabla = (TextView) setCosasPersonajeTabla.findViewById(R.id.txtEfectoPersonajeTabla);
		txtEfectoPersonajeTabla.setText(texto);
		btnQuitarLinea = (Button) setCosasPersonajeTabla.findViewById(R.id.btnEliminarfilaEfectoPersonajeTabla);
		btnQuitarLinea.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewGroup layout = (RelativeLayout) v.getParent();
				ViewGroup parent = (ViewGroup) ((ViewGroup) v.getParent()).getParent();
				parent.removeView(layout);
			}
		});
		
		scrollView.post(new Runnable() { 
            public void run() { 
            	scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            } 
		});
	}
	
	private void mostrarAgregarEfecto(View view) {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el t�tulo por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialogefecto);
        
        //Rellenar spinner de efectos con todos los efectos de la tabla
        final Spinner desplegableEfectoGrupo = (Spinner) customDialog.findViewById(R.id.spinnerEfectosGrupo); 
        db = new AdaptadorBD(getBaseContext());
		List<String> lista = new ArrayList<String>();
		List<String> listaF = new ArrayList<String>();
		lista.add("-----------------");
		listaF.add("-------FAV-------");
		// Obtener efectos favoritos
		try {
			db.abrir();
			Cursor cF = db.consulta("SELECT nombre FROM FAV_EFECTOS ORDER BY NOMBRE;", null);
			if (cF.moveToFirst()){
				do {
					listaF.add(cF.getString(0));
				} while (cF.moveToNext());
			}
			cF.close();
			Cursor c = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;",null);
			if (c.moveToFirst()) {
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}
			db.cerrar();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		listaF.addAll(lista);
		//Poner lista de efectos en desplegable de efectos
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaF);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		desplegableEfectoGrupo.setAdapter(adapter);
		
        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View view) {
            	//Comprobar que se ha seleccionado un efecto valido
            	if (desplegableEfectoGrupo.getSelectedItem().toString().equalsIgnoreCase("-----------------")){return;}
            	if (desplegableEfectoGrupo.getSelectedItemId() == 0){return;}
            	//Comprobar si el efecto esta presente en los personajes
            	try {
            		String efecto = desplegableEfectoGrupo.getSelectedItem().toString();
            		CrearFavorito(efecto);
            		//Conseguir id del efecto seleccionado
            		Cursor c1 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
            		c1.moveToFirst();
            		int idEfecto = c1.getInt(0);
            		c1.close();
            		db.begin();
            		//Recorrer tablas y comprobar si el buff lo tiene todo el mundo
            		for (int i=0; i < tablaGeneralGrupo.getChildCount(); i++){
            			TableRow row = (TableRow) tablaGeneralGrupo.getChildAt(i);
            			TextView nombre = (TextView) row.getChildAt(0); 
            			String personaje = nombre.getText().toString();
            			//Conseguir id del personaje
            			Cursor c11 = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';", null);
            			c11.moveToFirst();
            			int idPersonaje = c11.getInt(0);
            			c11.close();
            			//Comprobar si existe registro de efecto
            			Cursor c12 = db.consulta(
            					"SELECT COUNT(idPersonaje) FROM PERSONAJES_EFECTOS WHERE idPersonaje = " + idPersonaje + 
            					" AND idEfecto = "+ idEfecto + ";", null);
            			c12.moveToFirst();
            			int check = c12.getInt(0);
            			c12.close();
            			
            			if (check == 0){
            				//No existe, se crea con valor 1
            				db.insertar("INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
            							"(" + idPersonaje + ", " + idEfecto + ", 1);");
            			} else {
            				//Existe, se edita
            				db.insertar("UPDATE PERSONAJES_EFECTOS SET afectado = 1 " + 
            							"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfecto + ";" ); 
            			}
            		}
            		db.commit();
        		} catch (Exception e) {
        			e.printStackTrace();
        			db.rollback();
        		} finally {
        			db.rollback();
        			db.cerrar();
        		}
            	tablaGeneralGrupo.removeAllViews();
            	agregarFilasTabla();
            	customDialog.dismiss();
                Toast.makeText(getBaseContext(), "Efecto puesto en todos los personajes", Toast.LENGTH_SHORT).show();
            }
        });
         
        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
         
        customDialog.show();
    }
	
	private void mostrarEliminarEfecto(View view) {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el t�tulo por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialogefecto);
        
        //Rellenar spinner de efectos con todos los efectos de la tabla
        final Spinner desplegableEfectoGrupo = (Spinner) customDialog.findViewById(R.id.spinnerEfectosGrupo); 
        db = new AdaptadorBD(getBaseContext());
        List<String> lista = new ArrayList<String>();
		List<String> listaF = new ArrayList<String>();
		lista.add("-----------------");
		listaF.add("-------FAV-------");
		// Obtener efectos favoritos
		try {
			db.abrir();
			Cursor cF = db.consulta("SELECT nombre FROM FAV_EFECTOS ORDER BY NOMBRE;", null);
			if (cF.moveToFirst()){
				do {
					listaF.add(cF.getString(0));
				} while (cF.moveToNext());
			}
			cF.close();
			Cursor c = db.consulta("SELECT nombre FROM EFECTOS ORDER BY nombre;",null);
			if (c.moveToFirst()) {
				do {
					lista.add(c.getString(0));
				} while (c.moveToNext());
			}
			db.cerrar();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		listaF.addAll(lista);
		//Poner lista de efectos en desplegable de efectos
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaF);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		desplegableEfectoGrupo.setAdapter(adapter);
		
        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View view) {
            	//Comprobar que se ha seleccionado un efecto valido
            	if (desplegableEfectoGrupo.getSelectedItem().toString().equalsIgnoreCase("-----------------")){return;}
            	if (desplegableEfectoGrupo.getSelectedItemId() == 0){return;}
            	try {
            		String efecto = desplegableEfectoGrupo.getSelectedItem().toString();
            		//Conseguir id del efecto seleccionado
            		Cursor c1 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
            		c1.moveToFirst();
            		int idEfecto = c1.getInt(0);
            		c1.close();
            		db.begin();
            		//Recorrer tablas y comprobar si el buff lo tiene todo el mundo
            		for (int i=0; i < tablaGeneralGrupo.getChildCount(); i++){
            			TableRow row = (TableRow) tablaGeneralGrupo.getChildAt(i);
            			TextView nombre = (TextView) row.getChildAt(0); 
            			String personaje = nombre.getText().toString();
            			//Conseguir id del personaje
            			Cursor c11 = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';", null);
            			c11.moveToFirst();
            			int idPersonaje = c11.getInt(0);
            			c11.close();
            			//Comprobar si existe registro de efecto
            			Cursor c12 = db.consulta(
            					"SELECT COUNT(idPersonaje) FROM PERSONAJES_EFECTOS WHERE idPersonaje = " + idPersonaje + 
            					" AND idEfecto = "+ idEfecto + ";", null);
            			c12.moveToFirst();
            			int check = c12.getInt(0);
            			c12.close();
            			
            			if (check != 0){
            				//Existe, se edita
            				db.insertar("UPDATE PERSONAJES_EFECTOS SET afectado = 0 " + 
            							"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfecto + ";" ); 
            			}
            		}
            		db.commit();
        		} catch (Exception e) {
        			e.printStackTrace();
        			db.rollback();
        		} finally {
        			db.rollback();
        			db.cerrar();
        		}
            	tablaGeneralGrupo.removeAllViews();
            	agregarFilasTabla();
            	customDialog.dismiss();
                Toast.makeText(getBaseContext(), "Efecto quitado en todos los personajes", Toast.LENGTH_SHORT).show();
            }
        });
         
        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
         
        customDialog.show();
    }
	
	private boolean EfectoRepetido() {
		//Recorrer los childs del layout parent
		List<String> efectos = new ArrayList<String>();
		int count = layout.getChildCount();
		for (int i=0; i < count; i++){
			//Recoger de cada child la clase
			RelativeLayout cosas = (RelativeLayout) layout.getChildAt(i);
			TextView txtEfectoPersonajeTabla = (TextView) cosas.findViewById(R.id.txtEfectoPersonajeTabla);
			String efecto = txtEfectoPersonajeTabla.getText().toString();
			efectos.add(efecto);
		}
		//Recorrer array de strings a ver si hay dos iguales
		for(int i=0;i<efectos.size()-1;i++){
			for(int j=i+1;j<efectos.size();j++){
				if(efectos.get(i).equals(efectos.get(j))){
					Toast.makeText(getBaseContext(), "Efecto duplicado: " + efectos.get(i), Toast.LENGTH_SHORT).show();
					return true;
				}
			}
		}
		return false;
	}
	
	private void LabelBuffs(){
		db = new AdaptadorBD(getBaseContext());
		db.abrir();
		try {
			String buffsGrupo = "Efectos de grupo: ";
			Cursor clabel = db.consulta(
					"SELECT nombre FROM efectos i INNER JOIN " + 
					"(SELECT idefecto, COUNT(distinct idpersonaje) AS c FROM personajes_efectos " + 
					"WHERE afectado = 1 GROUP BY idefecto) a on i.idefecto = a.idefecto " + 
					"INNER JOIN (SELECT COUNT(*) as c FROM personajes) p ON p.c = a.c;"
					, null);
			if (clabel.moveToFirst()){
				do {
					buffsGrupo = buffsGrupo + clabel.getString(0) + " / ";
				} while (clabel.moveToNext());
			}
			clabel.close();
			labelEfectosGrupoTabla.setText(buffsGrupo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
	}
	
	private void CrearFavorito(String efecto){
		try {
			db.abrir();
			int idEfecto = 0;
			Cursor c1 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + efecto + "';", null);
			if (c1.moveToFirst()){
				idEfecto = c1.getInt(0);
			}
			c1.close();
			Cursor c2 = db.consulta("SELECT COUNT(idEfecto) FROM FAV_EFECTOS WHERE idEfecto = " + idEfecto + ";", null);
			c2.moveToFirst();
			int check = c2.getInt(0);
			c2.close();
			db.begin();
			if (check == 0){
				db.insertar("INSERT INTO FAV_EFECTOS (idEfecto, nombre) VALUES (" + idEfecto + ", '" + efecto + "')");
			} 
			db.commit();
		} catch (Exception e) {
			e.printStackTrace();
			db.rollback();
		} finally {
			db.rollback();
			db.cerrar();
		}
	}

	private void mostrarDesgloseArmadura (int idPersonaje, int modDestreza, int maxDesBonus, int modAcTamaño, int armorEscudo, int buffAC){
		// con este tema personalizado evitamos los bordes por defecto
		dialogAC = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el t�tulo por defecto
		dialogAC.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
		dialogAC.setCancelable(false);
        //establecemos el contenido de nuestro dialog
		dialogAC.setContentView(R.layout.dialog_ac_personaje);
		
		//Capturar efecto de "Maximo Armor buff (no se suman)" si es que existe
		int armorPersonaje = 0;
		Cursor carmorpj = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 2 AND idPersonaje = " + idPersonaje + 
				" AND afectado = 1;", null);
		if (carmorpj.moveToFirst()){
			do {
				armorPersonaje = carmorpj.getInt(0);
			} while (carmorpj.moveToNext());
		}
		carmorpj.close();
		
		//Capturar efecto de "Maximo shield buff" si es que existe
		int escudoPersonaje = 0;
		Cursor cescudopj = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 16 AND idPersonaje = " + idPersonaje + 
				" AND afectado = 1;", null);
		if (cescudopj.moveToFirst()){
			do {
				escudoPersonaje = cescudopj.getInt(0);
			} while (cescudopj.moveToNext());
		}
		cescudopj.close();
		
		//Capturar efecto de "Maximo natural armor" si es que existe
		int naturalArmor = 0;
		Cursor cnatural = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 12 AND idPersonaje = " + idPersonaje + 
				" AND afectado = 1;", null);
		if (cnatural.moveToFirst()){
			do {
				naturalArmor = cnatural.getInt(0);
			} while (cnatural.moveToNext());
		}
		cnatural.close();
		int dodgeBonus = 0;
		//Capturar efecto "Suma dodge" si es que existe
		Cursor dodge = db.consulta(
				"SELECT SUM(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 6 AND idPersonaje = " + idPersonaje + ";", null);
		if (dodge.moveToFirst()){ do { dodgeBonus += dodge.getInt(0); } while (dodge.moveToNext()); }
		
		/* ARMADURAS */
		int bonoDesArmadura = 0;
		if (modDestreza > maxDesBonus){
			bonoDesArmadura = maxDesBonus;
		} else {
			bonoDesArmadura = modDestreza;
		}
		
		int armorTotal = 10 + bonoDesArmadura + modAcTamaño + buffAC;
		int armorToque = 10 + bonoDesArmadura + modAcTamaño + buffAC - armorPersonaje - escudoPersonaje - naturalArmor;
		int armorDesprevenido = 10 + buffAC + modAcTamaño - dodgeBonus;
		int armorSinEscudo = 10 + bonoDesArmadura + modAcTamaño + buffAC - escudoPersonaje;
		
		TextView txtArmaduraPersonajeAC = (TextView) dialogAC.findViewById(R.id.txtArmaduraPersonajeAC);
		TextView txtArmaduraToquePersonajeAC = (TextView) dialogAC.findViewById(R.id.txtArmaduraToquePersonajeAC);
		TextView txtArmaduraDesprevenidoPersonajeAC = (TextView) dialogAC.findViewById(R.id.txtArmaduraDesprevenidoPersonajeAC);
		TextView txtArmaduraSinEscudoPersonajeAC = (TextView) dialogAC.findViewById(R.id.txtArmaduraSinEscudoPersonajeAC);
		
		txtArmaduraPersonajeAC.setText(Integer.toString(armorTotal));
		txtArmaduraToquePersonajeAC.setText(Integer.toString(armorToque));
		txtArmaduraDesprevenidoPersonajeAC.setText(Integer.toString(armorDesprevenido));
		txtArmaduraSinEscudoPersonajeAC.setText(Integer.toString(armorSinEscudo));
		
		Button btnAceptarPersonajeAC = (Button) dialogAC.findViewById(R.id.btnAceptarPersonajeAC);
		btnAceptarPersonajeAC.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialogAC.dismiss();
			}
		});
		
		dialogAC.show();
	}
	
	private void mostrarDesgloseAtaque(int idPersonaje, int atkBase, boolean finesse, int modDestreza, 
			int modFuerza, int idTamaño, int atkBuffs, int buffAC) {
		// con este tema personalizado evitamos los bordes por defecto
		dialogAtaque = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el t�tulo por defecto
		dialogAtaque.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
		dialogAtaque.setCancelable(false);
        //establecemos el contenido de nuestro dialog
		dialogAtaque.setContentView(R.layout.dialog_atk_personaje);
		
		int modAtkTamaño= 0;
		int modBMCTamaño = 0;
		int modDMCTamaño = 0;
		
		Cursor tamaño = db.consulta("SELECT atkBonus, cmdBonus, cmbBonus FROM TAMANOS WHERE idTamano = " + idTamaño + ";", null);
		if (tamaño.moveToFirst()){
			do {
				modAtkTamaño= tamaño.getInt(0);
				modDMCTamaño = tamaño.getInt(1);
				modBMCTamaño = tamaño.getInt(2);
			} while (tamaño.moveToNext());
		}
		tamaño.close();
		
		int modAtk = modFuerza;
		if (finesse == true) modAtk = modDestreza;
		
		int buffsDMC = 0;
		int modStatBMC = modFuerza;
		if (idTamaño <= 3){modStatBMC = modDestreza;}
		//Buffs circumstance, dodge y untyped (stackean)
		Cursor circDef = db.consulta(
				"SELECT SUM(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo in (3,6,19) AND idPersonaje = " + idPersonaje + ";", null);
		if (circDef.moveToFirst()){ do { buffsDMC += circDef.getInt(0); } while (circDef.moveToNext()); }
		//Buffs deflection, insight, luck, morale, profane y sacred (no stackean)
		//Deflection
		Cursor deflection = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 5 AND idPersonaje = " + idPersonaje + ";", null);
		if (deflection.moveToFirst()){do{ buffsDMC += deflection.getInt(0); } while (deflection.moveToNext());}
		//Insight
		Cursor insight = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 9 AND idPersonaje = " + idPersonaje + ";", null);
		if (insight.moveToFirst()){do{ buffsDMC += insight.getInt(0); } while (insight.moveToNext());}
		//Luck
		Cursor luck = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 10 AND idPersonaje = " + idPersonaje + ";", null);
		if (luck.moveToFirst()){do{ buffsDMC += luck.getInt(0); } while (luck.moveToNext());}
		//Morale
		Cursor morale = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 11 AND idPersonaje = " + idPersonaje + ";", null);
		if (morale.moveToFirst()){do{ buffsDMC += morale.getInt(0); } while (morale.moveToNext());}
		//Profane
		Cursor profane = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 13 AND idPersonaje = " + idPersonaje + ";", null);
		if (profane.moveToFirst()){do{ buffsDMC += profane.getInt(0); } while (profane.moveToNext());}
		//Sacred
		Cursor sacred = db.consulta(
				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 15 AND idPersonaje = " + idPersonaje + ";", null);
		if (sacred.moveToFirst()){do{ buffsDMC += sacred.getInt(0); } while (sacred.moveToNext());}
		
		String ataque = "";
		String ataqueD = "";
		int ataque1 = 0;
		int ataque2 = 0;
		int ataque3 = 0;
		int ataque4 = 0;
		
		/* ATAQUE CC */
		if (atkBase > 15){
			ataque1 = atkBase + modAtk + modAtkTamaño + atkBuffs;
			ataque2 = (atkBase-5) + modAtk + modAtkTamaño + atkBuffs;
			ataque3 = (atkBase-10) + modAtk + modAtkTamaño + atkBuffs;
			ataque4 = (atkBase-15) + modAtk + modAtkTamaño + atkBuffs;
			ataque = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3) + " / " + Integer.toString(ataque4);
		} else if (atkBase <= 15 && atkBase > 10){
			ataque1 = atkBase + modAtk + modAtkTamaño + atkBuffs;
			ataque2 = (atkBase-5) + modAtk + modAtkTamaño + atkBuffs;
			ataque3 = (atkBase-10) + modAtk + modAtkTamaño + atkBuffs;
			ataque = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3);
		} else if (atkBase <= 10 && atkBase > 5){
			ataque1 = atkBase + modAtk + modAtkTamaño + atkBuffs;
			ataque2 = (atkBase-5) + modAtk + modAtkTamaño + atkBuffs;
			ataque = Integer.toString(ataque1) + " / " + Integer.toString(ataque2);
		} else {
			ataque1 = atkBase + modAtk + modAtkTamaño + atkBuffs;
			ataque = Integer.toString(ataque1);
		}
		
		/* ATAQUE DISTANCIA */
		if (atkBase > 15){
			ataque1 = atkBase + modDestreza + modAtkTamaño + atkBuffs;
			ataque2 = (atkBase-5) + modDestreza + modAtkTamaño + atkBuffs;
			ataque3 = (atkBase-10) + modDestreza + modAtkTamaño + atkBuffs;
			ataque4 = (atkBase-15) + modDestreza + modAtkTamaño + atkBuffs;
			ataqueD = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3) + " / " + Integer.toString(ataque4);
		} else if (atkBase < 15 && atkBase > 10){
			ataque1 = atkBase + modDestreza + modAtkTamaño + atkBuffs;
			ataque2 = (atkBase-5) + modDestreza + modAtkTamaño + atkBuffs;
			ataque3 = (atkBase-10) + modDestreza + modAtkTamaño + atkBuffs;
			ataqueD = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3);
		} else if (atkBase < 10 && atkBase > 5){
			ataque1 = atkBase + modDestreza + modAtkTamaño + atkBuffs;
			ataque2 = (atkBase-5) + modDestreza + modAtkTamaño + atkBuffs;
			ataqueD = Integer.toString(ataque1) + " / " + Integer.toString(ataque2);
		} else {
			ataque1 = atkBase + modDestreza + modAtkTamaño + atkBuffs;
			ataqueD = Integer.toString(ataque1);
		}
		
		/* BMC */
		int bmc = atkBase + modStatBMC + modBMCTamaño + atkBuffs;
		/* DMC */
		int dmc = 10 + atkBase + modFuerza + modDestreza + modDMCTamaño + buffsDMC;
		
		TextView txtMeleePersonajeAtk = (TextView) dialogAtaque.findViewById(R.id.txtMeleePersonajeAtk);
		TextView txtDistanciaPersonajeAtk = (TextView) dialogAtaque.findViewById(R.id.txtDistanciaPersonajeAtk);
		TextView txtBMCPersonajeAtk = (TextView) dialogAtaque.findViewById(R.id.txtBMCPersonajeAtk);
		TextView txtDMCPersonajeAtk = (TextView) dialogAtaque.findViewById(R.id.txtDMCPersonajeAtk);
		
		txtMeleePersonajeAtk.setText(ataque);
		txtDistanciaPersonajeAtk.setText(ataqueD);
		txtBMCPersonajeAtk.setText(Integer.toString(bmc));
		txtDMCPersonajeAtk.setText(Integer.toString(dmc));
		
		Button btnAceptarPersonajeAC = (Button) dialogAtaque.findViewById(R.id.btnAceptarPersonajeAtk);
		btnAceptarPersonajeAC.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialogAtaque.dismiss();
			}
		});
		
		dialogAtaque.show();
	}
	
	@SuppressWarnings("unchecked")
	private void mostrarDesgloseArma(final String personaje, final int idPersonaje, int idTamaño, int idArma, int idArmaOff, int idEscudo) {
		// con este tema personalizado evitamos los bordes por defecto
		dialogArma = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el t�tulo por defecto
		dialogArma.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
		dialogArma.setCancelable(false);
        //establecemos el contenido de nuestro dialog
		dialogArma.setContentView(R.layout.dialog_arma_personaje);
		
		// Get the screen's density scale
		float scale = getResources().getDisplayMetrics().density;
		int escalaPixelL;
		int escalaPixelM;
		int escalaPixelS;
		// Convert the dps to pixels, based on density scale
		//Determine screen size
    	escalaPixelL = (int) (SIZE_G * scale + 0.5f);
		escalaPixelS = (int) (SIZE_S * scale + 0.5f);
		escalaPixelM = (int) (SIZE_M * scale + 0.5f);
		
		final TableLayout tablaPersonajeArma = (TableLayout) dialogArma.findViewById(R.id.tablaPersonajeArma);
		TableLayout cabeceraPersonajeArma = (TableLayout) dialogArma.findViewById(R.id.cabeceraPersonajeArma);
		
		layoutL = new TableRow.LayoutParams(escalaPixelL, TableRow.LayoutParams.WRAP_CONTENT);
		layoutS = new TableRow.LayoutParams(escalaPixelS, TableRow.LayoutParams.WRAP_CONTENT);
		layoutM = new TableRow.LayoutParams(escalaPixelM, TableRow.LayoutParams.WRAP_CONTENT);
		
		/* RELLENADO DE CELDAS DE LA CABECERA DE ARMAS */
		
		TableRow filaArma;
		TextView textoNombreArma;
		TextView textoDadoDmg;
		TextView textoCritico;
		
		filaArma = new TableRow(getBaseContext());
		
		textoNombreArma = new TextView(getBaseContext());
		textoDadoDmg = new TextView(getBaseContext());
		textoCritico = new TextView(getBaseContext());
		
		textoNombreArma.setText("ARMA");
		textoNombreArma.setGravity(Gravity.CENTER_VERTICAL);
		textoNombreArma.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoNombreArma.setTypeface(null, Typeface.BOLD);
		textoNombreArma.setLayoutParams(layoutL);
		
		textoDadoDmg.setText("DAÑO");
		textoDadoDmg.setGravity(Gravity.CENTER);
		textoDadoDmg.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoDadoDmg.setTypeface(null, Typeface.BOLD);
		textoDadoDmg.setLayoutParams(layoutM);
		
		textoCritico.setText("CRITICO");
		textoCritico.setGravity(Gravity.CENTER);
		textoCritico.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoCritico.setTypeface(null, Typeface.BOLD);
		textoCritico.setLayoutParams(layoutL);
		
		filaArma.addView(textoNombreArma);
		filaArma.addView(textoDadoDmg);
		filaArma.addView(textoCritico);

		cabeceraPersonajeArma.addView(filaArma);
		
		/* RELLENADO DE SPINNER DE ARMAS CON LISTA DE ARMAS DE LA BD */
		
		final Spinner spinnerArmaPersonajeArma = (Spinner) dialogArma.findViewById(R.id.spinnerArmaPersonajeArma);
		List<String> listaArmas = new ArrayList<String>();
		listaArmas.add("Seleccionar");
		//Obtener armas
		db.abrir();
		try {
			Cursor c = db.consulta("SELECT nombre FROM armas ORDER BY nombre;", null);
			if (c.moveToFirst()){
				do {
					listaArmas.add(c.getString(0));
				} while (c.moveToNext());
			}
			db.cerrar();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Poner lista de armas en desplegable de armas
		ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaArmas);
		// Specify the layout to use when the list of choices appears
		adapterArmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerArmaPersonajeArma.setAdapter(adapterArmas);
		
		/* RELLENADO SPINNER DE BONIFICADOR DE ESCUDO */
		
		final Spinner spinnerBonoEscudoPersonajeArma = (Spinner) dialogArma.findViewById(R.id.spinnerBonoEscudoPersonajeArma);
		
		List<String> listaBonos = new ArrayList<String>();
		listaBonos.add("Seleccionar");
		for (int i=0; i < 6; i++){
			listaBonos.add(Integer.toString(i));
		}
		//Poner bonos de armaduras en desplegable de bonos
		ArrayAdapter<String> adapterBonos = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaBonos);
		// Specify the layout to use when the list of choices appears
		adapterBonos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerBonoEscudoPersonajeArma.setAdapter(adapterBonos);
		
		/* RELLENADO SPINNER OFFHAND */
		
		final Spinner spinnerOffhandPersonajeArma = (Spinner) dialogArma.findViewById(R.id.spinnerOffhandPersonajeArma);
		
		List<String> listaOffhand = new ArrayList<String>();
		listaOffhand.add("Elegir");
		ArrayAdapter<String> adapterOffhand = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaOffhand);
		// Specify the layout to use when the list of choices appears
		adapterOffhand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerOffhandPersonajeArma.setAdapter(adapterOffhand);
		
		final RadioGroup rgTipoOffhandPersonajeArma = (RadioGroup) dialogArma.findViewById(R.id.rgTipoOffhandPersonajeArma);
		rgTipoOffhandPersonajeArma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int id = rgTipoOffhandPersonajeArma.getCheckedRadioButtonId();
				if (id == -1){
				    //No hay nada seleccionado
					List<String> listaOffhand = new ArrayList<String>();
					listaOffhand.add("Elegir");
					ArrayAdapter<String> adapterOffhand = new ArrayAdapter<String>(getBaseContext(),
					        android.R.layout.simple_spinner_item, listaOffhand);
					// Specify the layout to use when the list of choices appears
					adapterOffhand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					// Apply the adapter to the spinner
					spinnerOffhandPersonajeArma.setAdapter(adapterOffhand);
					spinnerOffhandPersonajeArma.setEnabled(false);
					spinnerBonoEscudoPersonajeArma.setEnabled(false);
				} else{
					if (id==R.id.rbSinOffhandPersonajeArma){
						//Ninguno
						List<String> listaNadaOffhand = new ArrayList<String>();
						listaNadaOffhand.add("NINGUNO");
						ArrayAdapter<String> adapterNadaOffhand = new ArrayAdapter<String>(getBaseContext(),
						        android.R.layout.simple_spinner_item, listaNadaOffhand);
						// Specify the layout to use when the list of choices appears
						adapterNadaOffhand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						// Apply the adapter to the spinner
						spinnerOffhandPersonajeArma.setAdapter(adapterNadaOffhand);
						spinnerOffhandPersonajeArma.setEnabled(false);
						spinnerBonoEscudoPersonajeArma.setEnabled(false);
					} else if (id == R.id.rbArmaOffhandPersonajeArma){
				        //Radio de arma offhand
				    	List<String> listaArmasOff = new ArrayList<String>();
				    	listaArmasOff.add("Seleccionar");
						//Obtener armas
						try {
							Cursor c = db.consulta("SELECT nombre FROM armas ORDER BY nombre;", null);
							if (c.moveToFirst()){
								do {
									listaArmasOff.add(c.getString(0));
								} while (c.moveToNext());
							}
							c.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						//Poner lista de armas en desplegable de armas
						ArrayAdapter<String> adapterArmasOff = new ArrayAdapter<String>(getBaseContext(),
						        android.R.layout.simple_spinner_item, listaArmasOff);
						// Specify the layout to use when the list of choices appears
						adapterArmasOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						// Apply the adapter to the spinner
						spinnerOffhandPersonajeArma.setAdapter(adapterArmasOff);
						spinnerOffhandPersonajeArma.setEnabled(true);
						spinnerBonoEscudoPersonajeArma.setEnabled(false);
				    } else {
				    	//Radio de escudo
				    	List<String> listaEscudos = new ArrayList<String>();
						listaEscudos.add("Seleccionar");
						//Obtener escudos
						try {
							Cursor c = db.consulta("SELECT nombre FROM armaduras WHERE bonoEscudo > 0 ORDER BY nombre;", null);
							if (c.moveToFirst()){
								do {
									listaEscudos.add(c.getString(0));
								} while (c.moveToNext());
							}
							db.cerrar();
							c.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						//Poner lista de armaduras en desplegable de armaduras
						ArrayAdapter<String> adapterEscudos = new ArrayAdapter<String>(getBaseContext(),
						        android.R.layout.simple_spinner_item, listaEscudos);
						// Specify the layout to use when the list of choices appears
						adapterEscudos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						// Apply the adapter to the spinner
						spinnerOffhandPersonajeArma.setAdapter(adapterEscudos);
						spinnerOffhandPersonajeArma.setEnabled(true);
						spinnerBonoEscudoPersonajeArma.setEnabled(true);
				    }
				}
			}
		});
		rgTipoOffhandPersonajeArma.clearCheck();
		
		/* SELECCIONAR EQUIPO DEL PERSONAJE */
		
		try {
			//Lista con dados de daño de arma segun tamaño del personaje
			String dados = "";
			switch (idTamaño){
	    		case 1:
	    			dados = "dmF";
	    			break;
	    		case 2:
	    			dados = "dmD";
	    			break;
	    		case 3:
	    			dados = "dmT";
	    			break;
	    		case 4:
	    			dados = "dmS";
	    			break;
	    		case 5:
	    			dados = "dmM";
	    			break;
	    		case 6:
	    			dados = "dmL";
	    			break;
	    		case 7:
	    			dados = "dmH";
	    			break;
	    		case 8:
	    			dados = "dmG";
	    			break;
	    		case 9:
	    			dados = "dmC";
	    			break;
			}
			final String dadosArma = dados;
			//Encontrar equipo del personaje
			spinnerArmaPersonajeArma.setSelection(0);
			spinnerOffhandPersonajeArma.setSelection(0);
			spinnerBonoEscudoPersonajeArma.setSelection(0);
			spinnerBonoEscudoPersonajeArma.setEnabled(false);
			if (idArma == 0){
				spinnerArmaPersonajeArma.setSelection(0);
			} else {
				try {
					//Buscar nombre de arma
					Cursor c12 = db.consulta("SELECT nombre FROM armas WHERE idArma = " + idArma + ";", null);
					c12.moveToFirst();
					String nombreArmaMain = c12.getString(0);
					c12.close();
					//Seleccionar arma
					int spinnerpositionArma = 0;
					ArrayAdapter<String> adapterArma;
					adapterArma = (ArrayAdapter<String>) spinnerArmaPersonajeArma.getAdapter(); //cast to an ArrayAdapter
					spinnerpositionArma = adapterArma.getPosition(nombreArmaMain);
					spinnerArmaPersonajeArma.setSelection(spinnerpositionArma);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (idEscudo > 0) {
				try {
					rgTipoOffhandPersonajeArma.check(R.id.rbEscudoOffhandPersonajeArma);
					//Buscar nombre de escudo
					Cursor c13 = db.consulta("SELECT nombre, bonoEscudo FROM armaduras WHERE idArmadura = " + idEscudo + ";", null);
					c13.moveToFirst();
					String nombreEscudo = c13.getString(0);
					int bonoEscudo = c13.getInt(1);
					c13.close();
					int bono = 0;
					//Calcular el bonificador de la armadura restando la armadura normal a la que tiene el personaje
					//Obtener efecto de armadura del personaje
					Cursor ccc = db.consulta(
							"SELECT EFECTOS_STATS.valor FROM EFECTOS_STATS, EFECTOS " + 
							"WHERE EFECTOS_STATS.idEFecto = EFECTOS.idEfecto AND " + 
							"EFECTOS.nombre = 'Escudo " + personaje + "';"
							, null);
					if (ccc.moveToFirst()){
						int bonoActual = ccc.getInt(0);
						bono = bonoActual - bonoEscudo;
					}
					ccc.close();
					//Seleccionar escudo
					int spinnerpositionEscudo = 0;
					ArrayAdapter<String> adapterEscudo = (ArrayAdapter<String>) spinnerOffhandPersonajeArma.getAdapter(); //cast to an ArrayAdapter
					spinnerpositionEscudo = adapterEscudo.getPosition(nombreEscudo);
					spinnerOffhandPersonajeArma.setSelection(spinnerpositionEscudo);
					//Seleccionar bonos
					int spinnerpositionBonoEscudo = 0;
					ArrayAdapter<String> adapterBonosEscudo;
					adapterBonosEscudo = (ArrayAdapter<String>) spinnerBonoEscudoPersonajeArma.getAdapter(); //cast to an ArrayAdapter
					spinnerpositionBonoEscudo = adapterBonosEscudo.getPosition(Integer.toString(bono));
					spinnerBonoEscudoPersonajeArma.setSelection(spinnerpositionBonoEscudo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			if (idArmaOff > 0) {
				try {
					rgTipoOffhandPersonajeArma.check(R.id.rbArmaOffhandPersonajeArma);
					//Buscar nombre, dados de golpe y critico de arma
					Cursor c14 = db.consulta("SELECT nombre FROM armas WHERE idArma = " + idArmaOff + ";", null);
					c14.moveToFirst();
					String nombreArmaOff = c14.getString(0);
					c14.close();
					//Seleccionar arma
					int spinnerpositionArmaOff = 0;
					ArrayAdapter<String> adapterArmaOff;
					adapterArmaOff = (ArrayAdapter<String>) spinnerOffhandPersonajeArma.getAdapter(); //cast to an ArrayAdapter
					spinnerpositionArmaOff = adapterArmaOff.getPosition(nombreArmaOff);
					spinnerOffhandPersonajeArma.setSelection(spinnerpositionArmaOff);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (idEscudo == 0 && idArma == 0){
				rgTipoOffhandPersonajeArma.check(R.id.rbSinOffhandPersonajeArma);
			}
			
			spinnerArmaPersonajeArma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				
				@Override
				public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
					if (arg0.getSelectedItemId() == 0){
						return;
					} else {
						tablaPersonajeArma.removeAllViews();
						String nombreArmaMain = spinnerArmaPersonajeArma.getSelectedItem().toString();
						Cursor c13 = db.consulta("SELECT " + dadosArma + ", critico FROM armas WHERE nombre = '" + nombreArmaMain + "';", null);
						c13.moveToFirst();
						String dadosDmgArmaMain = c13.getString(0);
						String criticoArmaMain = c13.getString(1);
						c13.close();
						
						//Poner fila en la tabla con datos del arma
						TableRow filaArmaMain;
			    		TextView textoNombreArmaMain;
			    		TextView textoDadoDmgArmaMain;
			    		TextView textoCriticoArmaMain;
			    		
			    		filaArmaMain = new TableRow(getBaseContext());
			    		
			    		textoNombreArmaMain = new TextView(getBaseContext());
			    		textoDadoDmgArmaMain = new TextView(getBaseContext());
			    		textoCriticoArmaMain = new TextView(getBaseContext());
			    		
			    		textoNombreArmaMain.setText(nombreArmaMain);
			    		textoNombreArmaMain.setGravity(Gravity.CENTER_VERTICAL);
			    		textoNombreArmaMain.setBackgroundResource(R.drawable.tabla_celda);
			    		textoNombreArmaMain.setTextColor(0xFF000000);
			    		textoNombreArmaMain.setLayoutParams(layoutL);
			    		
			    		textoDadoDmgArmaMain.setText(dadosDmgArmaMain);
			    		textoDadoDmgArmaMain.setGravity(Gravity.CENTER);
			    		textoDadoDmgArmaMain.setBackgroundResource(R.drawable.tabla_celda);
			    		textoDadoDmgArmaMain.setTextColor(0xFF000000);
			    		textoDadoDmgArmaMain.setLayoutParams(layoutM);
			    		
			    		textoCriticoArmaMain.setText(criticoArmaMain);
			    		textoCriticoArmaMain.setGravity(Gravity.CENTER);
			    		textoCriticoArmaMain.setBackgroundResource(R.drawable.tabla_celda);
			    		textoCriticoArmaMain.setTextColor(0xFF000000);
			    		textoCriticoArmaMain.setLayoutParams(layoutL);
			    		
			    		filaArmaMain.addView(textoNombreArmaMain);
			    		filaArmaMain.addView(textoDadoDmgArmaMain);
			    		filaArmaMain.addView(textoCriticoArmaMain);

			    		tablaPersonajeArma.addView(filaArmaMain);
						
						int pos = spinnerOffhandPersonajeArma.getSelectedItemPosition();
						if (pos > 0 && rgTipoOffhandPersonajeArma.getCheckedRadioButtonId() == R.id.rbArmaOffhandPersonajeArma){
							//Conseguir id del arma offhand seleccionada
							String nombreArmaOff = spinnerOffhandPersonajeArma.getSelectedItem().toString();
							Cursor c14 = db.consulta("SELECT " + dadosArma + ", critico FROM armas WHERE nombre = '" + nombreArmaOff + "';", null);
							c14.moveToFirst();
							String dadosDmgArmaOff = c14.getString(0);
							String criticoArmaOff = c14.getString(1);
							c14.close();
							
							//Poner fila en la tabla con datos del arma
							TableRow filaArmaOff;
				    		TextView textoNombreArmaOff;
				    		TextView textoDadoDmgArmaOff;
				    		TextView textoCriticoArmaOff;
				    		
				    		filaArmaOff = new TableRow(getBaseContext());
				    		
				    		textoNombreArmaOff = new TextView(getBaseContext());
				    		textoDadoDmgArmaOff = new TextView(getBaseContext());
				    		textoCriticoArmaOff = new TextView(getBaseContext());
				    		
				    		textoNombreArmaOff.setText(nombreArmaOff);
				    		textoNombreArmaOff.setGravity(Gravity.CENTER_VERTICAL);
				    		textoNombreArmaOff.setBackgroundResource(R.drawable.tabla_celda);
				    		textoNombreArmaOff.setTextColor(0xFF000000);
				    		textoNombreArmaOff.setLayoutParams(layoutL);
				    		
				    		textoDadoDmgArmaOff.setText(dadosDmgArmaOff);
				    		textoDadoDmgArmaOff.setGravity(Gravity.CENTER);
				    		textoDadoDmgArmaOff.setBackgroundResource(R.drawable.tabla_celda);
				    		textoDadoDmgArmaOff.setTextColor(0xFF000000);
				    		textoDadoDmgArmaOff.setLayoutParams(layoutM);
				    		
				    		textoCriticoArmaOff.setText(criticoArmaOff);
				    		textoCriticoArmaOff.setGravity(Gravity.CENTER);
				    		textoCriticoArmaOff.setBackgroundResource(R.drawable.tabla_celda);
				    		textoCriticoArmaOff.setTextColor(0xFF000000);
				    		textoCriticoArmaOff.setLayoutParams(layoutL);
				    		
				    		filaArmaOff.addView(textoNombreArmaOff);
				    		filaArmaOff.addView(textoDadoDmgArmaOff);
				    		filaArmaOff.addView(textoCriticoArmaOff);
				    		
				    		tablaPersonajeArma.addView(filaArmaOff);
						}
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					return;
				}
			});
			
			spinnerOffhandPersonajeArma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				
				@Override
				public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
					if (arg0.getSelectedItemId() == 0){
						return;
					} else {
						tablaPersonajeArma.removeAllViews();
						String nombreArmaMain = spinnerArmaPersonajeArma.getSelectedItem().toString();
						Cursor c13 = db.consulta("SELECT " + dadosArma + ", critico FROM armas WHERE nombre = '" + nombreArmaMain + "';", null);
						c13.moveToFirst();
						String dadosDmgArmaMain = c13.getString(0);
						String criticoArmaMain = c13.getString(1);
						c13.close();
						
						//Poner fila en la tabla con datos del arma
						TableRow filaArmaMain;
			    		TextView textoNombreArmaMain;
			    		TextView textoDadoDmgArmaMain;
			    		TextView textoCriticoArmaMain;
			    		
			    		filaArmaMain = new TableRow(getBaseContext());
			    		
			    		textoNombreArmaMain = new TextView(getBaseContext());
			    		textoDadoDmgArmaMain = new TextView(getBaseContext());
			    		textoCriticoArmaMain = new TextView(getBaseContext());
			    		
			    		textoNombreArmaMain.setText(nombreArmaMain);
			    		textoNombreArmaMain.setGravity(Gravity.CENTER_VERTICAL);
			    		textoNombreArmaMain.setBackgroundResource(R.drawable.tabla_celda);
			    		textoNombreArmaMain.setTextColor(0xFF000000);
			    		textoNombreArmaMain.setLayoutParams(layoutL);
			    		
			    		textoDadoDmgArmaMain.setText(dadosDmgArmaMain);
			    		textoDadoDmgArmaMain.setGravity(Gravity.CENTER);
			    		textoDadoDmgArmaMain.setBackgroundResource(R.drawable.tabla_celda);
			    		textoDadoDmgArmaMain.setTextColor(0xFF000000);
			    		textoDadoDmgArmaMain.setLayoutParams(layoutM);
			    		
			    		textoCriticoArmaMain.setText(criticoArmaMain);
			    		textoCriticoArmaMain.setGravity(Gravity.CENTER);
			    		textoCriticoArmaMain.setBackgroundResource(R.drawable.tabla_celda);
			    		textoCriticoArmaMain.setTextColor(0xFF000000);
			    		textoCriticoArmaMain.setLayoutParams(layoutL);
			    		
			    		filaArmaMain.addView(textoNombreArmaMain);
			    		filaArmaMain.addView(textoDadoDmgArmaMain);
			    		filaArmaMain.addView(textoCriticoArmaMain);

			    		tablaPersonajeArma.addView(filaArmaMain);
						
						int pos = spinnerOffhandPersonajeArma.getSelectedItemPosition();
						if (pos > 0 && rgTipoOffhandPersonajeArma.getCheckedRadioButtonId() == R.id.rbArmaOffhandPersonajeArma){
							//Conseguir id del arma offhand seleccionada
							String nombreArmaOff = spinnerOffhandPersonajeArma.getSelectedItem().toString();
							Cursor c14 = db.consulta("SELECT " + dadosArma + ", critico FROM armas WHERE nombre = '" + nombreArmaOff + "';", null);
							c14.moveToFirst();
							String dadosDmgArmaOff = c14.getString(0);
							String criticoArmaOff = c14.getString(1);
							c14.close();
							
							//Poner fila en la tabla con datos del arma
							TableRow filaArmaOff;
				    		TextView textoNombreArmaOff;
				    		TextView textoDadoDmgArmaOff;
				    		TextView textoCriticoArmaOff;
				    		
				    		filaArmaOff = new TableRow(getBaseContext());
				    		
				    		textoNombreArmaOff = new TextView(getBaseContext());
				    		textoDadoDmgArmaOff = new TextView(getBaseContext());
				    		textoCriticoArmaOff = new TextView(getBaseContext());
				    		
				    		textoNombreArmaOff.setText(nombreArmaOff);
				    		textoNombreArmaOff.setGravity(Gravity.CENTER_VERTICAL);
				    		textoNombreArmaOff.setBackgroundResource(R.drawable.tabla_celda);
				    		textoNombreArmaOff.setTextColor(0xFF000000);
				    		textoNombreArmaOff.setLayoutParams(layoutL);
				    		
				    		textoDadoDmgArmaOff.setText(dadosDmgArmaOff);
				    		textoDadoDmgArmaOff.setGravity(Gravity.CENTER);
				    		textoDadoDmgArmaOff.setBackgroundResource(R.drawable.tabla_celda);
				    		textoDadoDmgArmaOff.setTextColor(0xFF000000);
				    		textoDadoDmgArmaOff.setLayoutParams(layoutM);
				    		
				    		textoCriticoArmaOff.setText(criticoArmaOff);
				    		textoCriticoArmaOff.setGravity(Gravity.CENTER);
				    		textoCriticoArmaOff.setBackgroundResource(R.drawable.tabla_celda);
				    		textoCriticoArmaOff.setTextColor(0xFF000000);
				    		textoCriticoArmaOff.setLayoutParams(layoutL);
				    		
				    		filaArmaOff.addView(textoNombreArmaOff);
				    		filaArmaOff.addView(textoDadoDmgArmaOff);
				    		filaArmaOff.addView(textoCriticoArmaOff);
				    		
				    		tablaPersonajeArma.addView(filaArmaOff);
						}
						
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					return;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* BOTONES ACEPTAR Y CANCELAR */
		
		Button btnAceptarPersonajeArma = (Button) dialogArma.findViewById(R.id.btnAceptarPersonajeArma);
		btnAceptarPersonajeArma.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					db.abrir();
					db.begin();
					//Arma
					int idArma = 0;
					if(spinnerArmaPersonajeArma.getSelectedItemPosition() > 0){
						String armaSel = spinnerArmaPersonajeArma.getSelectedItem().toString();
						//Encontrar id del arma seleccionada
						Cursor c2 = db.consulta("SELECT idArma FROM ARMAS WHERE nombre = '" + armaSel + "';", null);
						c2.moveToFirst();
						idArma = c2.getInt(0);
						c2.close();
					}
					//Offhand
					int idOffArma = 0;
					int idOffEscudo = 0;
					int acEscudo = 0;
					int idEfectoEscudo = 0;
					String nombreEfectoEscudo = "Escudo " + personaje;
					Cursor esc = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + nombreEfectoEscudo + "';", null);
					if (esc.moveToFirst()){
						do {
							idEfectoEscudo = esc.getInt(0);
						} while (esc.moveToNext());
					}
					esc.close();
					int id = rgTipoOffhandPersonajeArma.getCheckedRadioButtonId();
					if (id == R.id.rbArmaOffhandPersonajeArma){
						if (spinnerOffhandPersonajeArma.getSelectedItemPosition() == 0){
							Toast.makeText(getBaseContext(), "Seleccione un arma valida", Toast.LENGTH_SHORT).show();
						} else {
							String armaOffSel = spinnerOffhandPersonajeArma.getSelectedItem().toString();
							//Encontrar id del arma seleccionada
							Cursor c3 = db.consulta("SELECT idArma FROM ARMAS WHERE nombre = '" + armaOffSel + "';", null);
							c3.moveToFirst();
							idOffArma = c3.getInt(0);
							c3.close();
							//Desactivar el efecto de "Escudo nombrepersonaje" en caso de que exista
							db.insertar(
									"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoEscudo + 
									" AND idPersonaje = " + idPersonaje + ";");
						}
					} else if (id == R.id.rbEscudoOffhandPersonajeArma) {
						if (spinnerOffhandPersonajeArma.getSelectedItemPosition() == 0){
							Toast.makeText(getBaseContext(), "Seleccione un escudo valido", Toast.LENGTH_SHORT).show();
						} else {
							String escudoOffSel = spinnerOffhandPersonajeArma.getSelectedItem().toString();
							//Encontrar id del arma seleccionada
							Cursor c4 = db.consulta("SELECT idArmadura, bonoEscudo FROM ARMADURAS WHERE nombre = '" + escudoOffSel + "';", null);
							c4.moveToFirst();
							idOffEscudo = c4.getInt(0);
							acEscudo = c4.getInt(1);
							c4.close();
							int bonoEscudo = 0;
							if (spinnerBonoEscudoPersonajeArma.getSelectedItemId()>0){
								bonoEscudo = Integer.parseInt(spinnerBonoEscudoPersonajeArma.getSelectedItem().toString());
							}
							acEscudo += bonoEscudo;
							if (idEfectoEscudo > 0){
								//Actualizar el valor del efecto
								db.insertar("UPDATE EFECTOS_STATS SET valor = " + acEscudo + " WHERE idEfecto = " + idEfectoEscudo + ";");
								//Comprobar si el personaje ya tiene asignado ese efecto
								Cursor c73 = db.consulta(
										"SELECT COUNT(idPersonaje) FROM PERSONAJES_EFECTOS " + 
										"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfectoEscudo + ";", null);
								c73.moveToFirst();
								if (c73.getInt(0) == 0){
									//No lo tiene, se le asigna
									db.insertar(
											"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
											"(" + idPersonaje + ", " + idEfectoEscudo + ", 1);");
								} else {
									//Si lo tiene, se actualiza el estado
									db.insertar(
											"UPDATE PERSONAJES_EFECTOS SET afectado = 1 " + 
											"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfectoEscudo + ";");
								}
								c73.close();
							} else {
								//Crear efecto "Escudo Nombrepersonaje"
								db.insertar("INSERT INTO EFECTOS (nombre) VALUES ('" + nombreEfectoEscudo + "');");
								//Conseguir el id del efecto recien creado
								Cursor c71 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE rowid = last_insert_rowid();", null);
								c71.moveToFirst(); 
								int idEfecto = c71.getInt(0); 
								c71.close();
								//Crear asociacion de stats, tipo y valor del efecto recien creado
								db.insertar("INSERT INTO EFECTOS_STATS (idEfecto, idStat, idTipo, valor) VALUES "
										+ "(" + idEfecto + ", " + 7 + ", " + 16 + ", " + acEscudo + ");");
								//Asignar el efecto recien creado al personaje
								db.insertar(
										"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
										"(" + idPersonaje + ", " + idEfecto + ", 1);");
							}
						}
					} else if (id == R.id.rbSinOffhandPersonajeArma){
						if (idEfectoEscudo > 0){
							db.insertar(
								"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoEscudo + 
								" AND idPersonaje = " + idPersonaje + ";");
						}
					} else {
						if (idEfectoEscudo > 0){
							db.insertar(
								"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoEscudo + 
								" AND idPersonaje = " + idPersonaje + ";");
						}
					}
					//Encontrar si hay linea de registro de equipo para el personaje
					Cursor c5 = db.consulta("SELECT COUNT(idPersonaje) FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
					int existe = 0;
					if (c5.moveToFirst()){
						do {
							 existe = c5.getInt(0);
						} while (c5.moveToNext());
					}
					c5.close();
					String mensaje = "";
					if (existe == 0){
						//No existe, se crea
						db.insertar("INSERT INTO PERSONAJES_EQUIPO (idPersonaje, mainhand, offEscudo, offArma) VALUES " + 
									"(" + idPersonaje + ", " + idArma + ", " + idOffEscudo + ", " + idOffArma + ");");
						mensaje = "Equipacion creada con exito";
					} else {
						//Ya existe, se actualiza
						db.insertar(
								"UPDATE PERSONAJES_EQUIPO " + 
								"SET mainhand = " + idArma + ", offEscudo = " + idOffEscudo + ", offArma = " + idOffArma + " " +   
								"WHERE idPersonaje = " + idPersonaje + ";");
						mensaje = "Equipacion editada con exito";
					}
					db.commit();
					Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
					db.rollback();
				} finally {
					db.rollback();
					db.cerrar();
					tablaGeneralGrupo.removeAllViews();
					agregarFilasTabla();
					dialogArma.dismiss();
				}
			}
		});
		
		Button btnCancelarPersonajeArma = (Button) dialogArma.findViewById(R.id.btnCancelarPersonajeArma);
		btnCancelarPersonajeArma.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialogArma.dismiss();
				db.cerrar();
			}
		});
		
		dialogArma.show();
	}
}
