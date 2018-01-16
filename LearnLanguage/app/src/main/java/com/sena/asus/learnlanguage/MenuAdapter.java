package  com.sena.asus.learnlanguage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context mContext;
    private List<Menu> menuList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView thumbnail;


        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos==0){
                        Intent intentLearnWords= new Intent(view.getContext(),LearnWords.class);
                        mContext.startActivity(intentLearnWords);
                    }else if(pos==1){
                        Intent intentAddnewWord= new Intent(view.getContext(),MyWordWorld.class);
                        mContext.startActivity(intentAddnewWord);
                    }else if(pos==2){
                        Intent intentQuizActivity= new Intent(view.getContext(),QuizActivity.class);
                        mContext.startActivity(intentQuizActivity);
                    }


                }

            });

        }
    }

    public MenuAdapter(Context mContext, List<Menu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.title.setText(menu.getName());

        // loading menu cover using Glide library
        Glide.with(mContext).load(menu.getThumbnail()).into(holder.thumbnail);




    }





    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
      //  popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
  /*  class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
*/
    @Override
    public int getItemCount() {
        return menuList.size();
    }
}