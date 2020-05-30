package adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyRecipesArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mFoods;
    private String[] mBase;

    public MyRecipesArrayAdapter(Context mContext, int resource, String[] mFoods, String[] mBase){
        super(mContext, resource);
        this.mContext = mContext;
        this.mFoods = mFoods;
        this.mBase = mBase;
    }

    @Override
    public Object getItem(int position){
        String foods = mFoods[position];
        String base = mBase[position];
        return String.format("%s \nBase category: %s",foods,  base);

    }

    @Override
    public int getCount(){
        return mFoods.length;
    }
}



