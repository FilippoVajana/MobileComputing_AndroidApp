package filippovajana.mcproject.helper;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import filippovajana.mcproject.fragment.MapFragment;
import filippovajana.mcproject.fragment.ProfileFragment;
import filippovajana.mcproject.fragment.StatusUpdateFragment;

public class FragmentHelper
{
    //app fragments dictionary
    public static Dictionary<String, Fragment> fragmentDictionary = new Hashtable()
    {{
        put("Profile", new ProfileFragment());
        put("Status", new StatusUpdateFragment());
        put("Map", new MapFragment());
    }};


    public static void loadFragment(FragmentManager manager, Fragment fragment, String fragmentTag, FrameLayout container)
    {
        //ensure current transaction finishes
        manager.executePendingTransactions();

        Logger.getLogger("FragmentHelper").log(Level.INFO, String.format("%d", fragment.getId()));

        //check if fragment was already added
        if (manager.findFragmentById(fragment.getId()) == null)
        {
            //create transaction
            FragmentTransaction transaction = manager.beginTransaction();
            //replace current fragment
            transaction.replace(container.getId(), fragment, fragmentTag);
            //commit
            transaction.commit();
            Logger.getLogger("FragmentHelper").log(Level.INFO, String.format("%s Loaded", fragment.getTag()));
        }
        else
        {
            Logger.getLogger("FragmentHelper").log(Level.INFO, String.format("%s Already Loaded", fragmentTag));
        }
    }
}
