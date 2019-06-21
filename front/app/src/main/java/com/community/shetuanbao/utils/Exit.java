package com.community.shetuanbao.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class Exit {

	public static List<Activity> activity=new LinkedList<Activity>();
	private static Exit instance;

	private Exit(){}

	public static Exit getInstance()
	{
		if(null == instance)
		{
			instance = new Exit();
		}

		return instance;
	}

	public void addActivities(Activity ac)
	{
		activity.add(ac);
	}

	public static void exitActivity()
	{
		for(Activity ac:activity)
		{
			ac.finish();
		}
	}
}