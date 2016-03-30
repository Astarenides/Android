package com.utiles.tiradas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
	private Dialog customDialog = null;
	AdaptadorBD db;
	private boolean doubleBackToExitPressedOnce = false;
	Fragment fragment;
	FragmentManager fragmentManager;
	private ActionBarDrawerToggle menuDrawerToggle;
	AdapterMenuItem navAdapter;
	private DrawerLayout navDrawerLayout;
	private ListView navList;
	private String[] titulos;

	private void MostrarFragment(int position) {
		fragmentManager = getFragmentManager();
		fragment = null;
		switch (position) {
		case 1:
			fragment = new Inicio();
			break;
		case 2:
			fragment = new Estadisticas();
			break;
		case 3:
			fragment = new Personajes();
			break;
		case 4:
			fragment = new Efectos();
			break;
		default:
			Toast.makeText( getBaseContext(), "Opcion " + this.titulos[(position - 1)] + " no disponible!", Toast.LENGTH_SHORT).show();
			this.fragment = new Inicio();
			position = 1;
		}
		FragmentTransaction localFragmentTransaction = this.fragmentManager.beginTransaction();
		localFragmentTransaction.replace(R.id.content_frame, this.fragment);
		localFragmentTransaction.addToBackStack(null);
		localFragmentTransaction.commit();
		navList.setItemChecked(position, true);
		navList.setSelection(position);
		setTitle(titulos[(position - 1)]);
		navDrawerLayout.closeDrawer(navList);


	}

	public void CopyDB(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
		// ---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];
		int length;
		while ((length = paramInputStream.read(buffer)) > 0) {
			paramOutputStream.write(buffer, 0, length);
		}
		paramInputStream.close();
		paramOutputStream.close();
	}

	public void MostrarAcercaDe() {
		// con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialogacercade);
		Button btnok = (Button) customDialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				customDialog.dismiss();
			}
		});
		this.customDialog.show();
	}

	public void ResetBD() {
		try {
			File localFile1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/com.utiles.tiradas/databases");
			localFile1.mkdirs();
			File localFile2 = new File(localFile1, "tiradas");
			localFile2.delete();
			CopyDB(getBaseContext().getAssets().open("tiradas"), new FileOutputStream(localFile2));
			Toast.makeText(getBaseContext(), "Base de datos actualizada.", Toast.LENGTH_SHORT).show();
			return;
		} catch (FileNotFoundException localFileNotFoundException) {
			localFileNotFoundException.printStackTrace();
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	public void onBackPressed() {
		if (this.doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}
		doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Pulsa atras de nuevo para salir", Toast.LENGTH_SHORT).show();
	}

	public void onConfigurationChanged(Configuration paramConfiguration) {
		super.onConfigurationChanged(paramConfiguration);
		menuDrawerToggle.onConfigurationChanged(paramConfiguration);
	}

	@SuppressLint({ "InflateParams" })
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.main);
		
		navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navList = (ListView) findViewById(R.id.listaMenu);
		View header = getLayoutInflater().inflate(R.layout.header, null);
		navList.addHeaderView(header);
		TypedArray navIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
		titulos = getResources().getStringArray(R.array.nav_options);
		ArrayList<ObjetoMenu> navItems = new ArrayList<ObjetoMenu>();
		navItems.add(new ObjetoMenu(titulos[0], navIcons.getResourceId(0, -1)));
		navItems.add(new ObjetoMenu(titulos[1], navIcons.getResourceId(1, -1)));
		navItems.add(new ObjetoMenu(titulos[2], navIcons.getResourceId(2, -1)));
		navItems.add(new ObjetoMenu(titulos[3], navIcons.getResourceId(3, -1)));
		navIcons.recycle();
		navAdapter = new AdapterMenuItem(this, navItems);
		navList.setAdapter(navAdapter);
		menuDrawerToggle = new ActionBarDrawerToggle(this,
				this.navDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.app_name) {
			public void onDrawerClosed(View paramAnonymousView) {
				getActionBar().setTitle(getResources().getString(R.string.drawer_open));
				MainActivity.this.supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View paramAnonymousView) {
				getActionBar().setTitle("Menu");
				supportInvalidateOptionsMenu();
			}
		};
		navDrawerLayout.setDrawerListener(menuDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> navList, View view, int position, long id) {
				MostrarFragment(position);
			}
		});
		try {
			File localFile1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/com.utiles.tiradas/databases");
			localFile1.mkdirs();
			File localFile2 = new File(localFile1, "tiradas");
			if (!localFile2.exists()) {
				CopyDB(getBaseContext().getAssets().open("tiradas"), new FileOutputStream(localFile2));
			}
			this.fragmentManager = getFragmentManager();
			this.fragment = this.fragmentManager.findFragmentById(2131034347);
			if (this.fragment == null) {
				MostrarFragment(1);
			}
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
			return;
		} catch (FileNotFoundException localFileNotFoundException) {
			localFileNotFoundException.printStackTrace();
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.menu, paramMenu);
		return true;
	}

	public void onDestroy() {
		super.onDestroy();
		try {
			this.db = new AdaptadorBD(getBaseContext());
			this.db.abrir();
			this.db.begin();
			this.db.insertar("DELETE FROM FAV_EFECTOS;");
			this.db.commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.db.rollback();
			return;
		} finally {
			this.db.rollback();
			this.db.cerrar();
		}
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (this.menuDrawerToggle.onOptionsItemSelected(paramMenuItem)) {
			return true;
		}
		switch (paramMenuItem.getItemId()) {
		case R.id.MnuOpc1:
			new AlertDialog.Builder(this)
					.setIcon(17301543)
					.setTitle("ATENCION")
					.setMessage("Reiniciar la base de datos borrara TODOS LOS DATOS introducidos por el usuario. ¿Está seguro?")
					.setPositiveButton("SI",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface paramAnonymousDialogInterface,int paramAnonymousInt) {
								ResetBD();
							}
						}).setNegativeButton("NO", null).show();
			return true;
		case R.id.MnuOpc2:
			MostrarAcercaDe();
			return true;
		default:
			return super.onOptionsItemSelected(paramMenuItem);
		
		}
	}

	protected void onPostCreate(Bundle paramBundle) {
		super.onPostCreate(paramBundle);
		this.menuDrawerToggle.syncState();
	}

	protected void onResume() {
		super.onResume();
		this.doubleBackToExitPressedOnce = false;
	}

}