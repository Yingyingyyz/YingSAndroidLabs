package algonquin.cst2335.zhao0211;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageListFragment extends Fragment {

    ArrayList<ChatMessage> messages = new ArrayList<>();
    private MyChatAdapter adt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);

        RecyclerView chatList = chatLayout.findViewById(R.id.myrecycler);
        EditText messageText = chatLayout.findViewById(R.id.editTextTextPersonName);

        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(linearLayoutManager);

        send = chatLayout.findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = messageText.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                ChatMessage chatMessage = new ChatMessage(string, 1, currentDateandTime);
                messages.add(chatMessage);
                messageText.setText("");
                adt.notifyItemInserted(messages.size() - 1);
            }
        });

        Button button2 = chatLayout.findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            String string = messageText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(string, 2, currentDateandTime);
            messages.add(chatMessage);
            messageText.setText("");
            adt.notifyItemInserted(messages.size() - 1);
        });

        return chatLayout;
    }

    Button send;

    public void notifyMessageDeleted(ChatMessage chosenMessage, int chosenPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete the message: " + chosenMessage.getMessage())
                .setTitle("Danger!:")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChatMessage removedMessage = messages.get(chosenPosition);
                        messages.remove(chosenPosition);
                        adt.notifyItemRemoved(chosenPosition);
                        Snackbar.make(send, "You delete message #" + chosenPosition, Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        messages.add(chosenPosition, removedMessage);
                                        adt.notifyItemInserted(chosenPosition);
                                    }
                                })
                                .show();
                    }
                })
                .create().show();
    }

    class MyRowViews extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowViews(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(v -> {
                ChatRoom parentActivity = (ChatRoom) getContext();
                int position = getAdapterPosition();
                parentActivity.userClickedMessage(messages.get(position), position);
            });
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {

        @NonNull
        @Override
        public MyRowViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            int layoutID;
            if (viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receiver_message;
            View loadedRow = layoutInflater.inflate(layoutID, parent, false);

            return new MyRowViews(loadedRow);
        }

        @Override
        public int getItemViewType(int position) {
            return messages.get(position).sendOrReceiver;
        }

        @Override
        public void onBindViewHolder(@NonNull MyRowViews holder, int position) {
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }
    }

    public class ChatMessage {
        String message;
        int sendOrReceiver;
        String timeSent;
        long id;

        public ChatMessage(String message, int sendOrReceiver, String timeSent) {
            this.message = message;
            this.sendOrReceiver = sendOrReceiver;
            this.timeSent = timeSent;
        }

        public ChatMessage(String message, int sendOrReceiver, String timeSent, long id) {
            this.message = message;
            this.sendOrReceiver = sendOrReceiver;
            this.timeSent = timeSent;
            setId(id);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getSendOrReceiver() {
            return sendOrReceiver;
        }

        public void setSendOrReceiver(int sendOrReceiver) {
            this.sendOrReceiver = sendOrReceiver;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public void setTimeSent(String timeSent) {
            this.timeSent = timeSent;
        }
    }
}
