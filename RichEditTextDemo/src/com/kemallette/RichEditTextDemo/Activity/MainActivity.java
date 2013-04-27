package com.kemallette.RichEditTextDemo.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.kemallette.RichEditTextDemo.R;
import com.kemallette.RichEditTextDemo.Utils.ListItem;
import com.kemallette.RichEditTextDemo.Utils.SimpleListItem;


public class MainActivity	extends
							SherlockListActivity implements
												OnItemClickListener{


	private ListItem[]		lItems	= new ListItem[] {


														new SimpleListItem(	"RichEditText",
																			EditingExampleActivity.class),
						new SimpleListItem(	"Custom Fonts",
											ValidationsExampleActivity.class),
						new SimpleListItem(	"Validations",
											ValidationsExampleActivity.class) };


	private final String[]	stringItems;


	public MainActivity(){

		stringItems = new String[lItems.length];
		for (int i = 0; i < lItems.length; i++)
			stringItems[i] = lItems[i].getListTitle();
	}


	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

		ListView mLv = new ListView(this);
		mLv.setId(android.R.id.list);

		setContentView(mLv); // Don't try this at home :)

		setListAdapter(new ArrayAdapter<String>(this,
												android.R.layout.simple_list_item_1,
												stringItems));
		getListView().setOnItemClickListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu){

		getSupportMenuInflater().inflate(	R.menu.menu,
											menu);
		return true;
	}


	@Override
	public boolean
		onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){

		switch(item.getItemId()){
			case R.id.prefs:
				startActivity(new Intent(	this,
											SettingsActivity.class));
				return true;

			default:
				return false;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> av, View v, int pos, long id){

		lItems[pos].goToDemo(this);
	}


}
