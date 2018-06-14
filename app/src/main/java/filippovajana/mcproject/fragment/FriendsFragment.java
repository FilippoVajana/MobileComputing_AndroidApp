package filippovajana.mcproject.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import filippovajana.mcproject.R;
import filippovajana.mcproject.adapter.AppFriendAdapter;
import filippovajana.mcproject.helper.SystemHelper;
import filippovajana.mcproject.model.AppDataModel;
import filippovajana.mcproject.model.AppFriend;

public class FriendsFragment extends Fragment
{
    //view
    View _view;

    //data model
    AppDataModel _dataModel;

    public FriendsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //init data model
        _dataModel = AppDataModel.getInstance();

        //update friends list
        updateFriendsListAsync();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_friends_list, container, false);


        //set ListView adapter
        setFriendsListAdapter();

        return _view;
    }


    private void setFriendsListAdapter()
    {
        ArrayList<AppFriend> list = _dataModel.get_friendsList();

        //build list adapter
        AppFriendAdapter adapter = new AppFriendAdapter(this.getActivity(), list);

        //set list adapter
        ListView listView = (ListView) _view.findViewById(R.id.friendsListView);
        listView.setAdapter(adapter);

        //check for empty list
        Snackbar.make(_view, String.format("%d Friends", list.size()), Snackbar.LENGTH_LONG)
                .show();
    }

    private void updateFriendsListAsync()
    {
        //build task
        Thread updateThread = new Thread(() ->
        {
            //update list
            _dataModel.updateFriendsList();
        });

        //run task
        updateThread.start();
        try
        {
            updateThread.join();
        }catch (Exception e)
        {
            SystemHelper.logError(this.getClass(), "Exception during friends list update");
        }
    }

}
