package algonquin.cst2335.zhao0211;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author : yjz
 * @date : 11/19/21 6:01 PM
 * @des :
 */
public class MessageDetailsFragment extends Fragment {

    MessageListFragment.ChatMessage chosenMessage;
    int chosenPosition;

    public MessageDetailsFragment(MessageListFragment.ChatMessage message, int position) {
        chosenMessage = message;
        chosenPosition = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.details_layout, container, false);
        TextView messageView = detailsView.findViewById(R.id.messageView);
        TextView sendView = detailsView.findViewById(R.id.sendView);
        TextView timeView = detailsView.findViewById(R.id.timeView);
        TextView idView = detailsView.findViewById(R.id.databaseIdView);

        messageView.setText("Message is: " + chosenMessage.getMessage());
        sendView.setText("Send or Receive? " + chosenMessage.getSendOrReceiver());
        timeView.setText("Time send: " + chosenMessage.getTimeSent());
        idView.setText("Database id is: " + chosenMessage.getId());

        Button closeButton = detailsView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        Button deleteButton =detailsView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            ChatRoom parentActivity = (ChatRoom) getContext();
            parentActivity.notifyMessageDelete(chosenMessage,chosenPosition);
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });
        return detailsView;
    }
}
