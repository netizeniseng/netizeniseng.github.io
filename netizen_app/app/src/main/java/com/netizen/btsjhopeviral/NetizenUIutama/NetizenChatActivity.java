package com.netizen.btsjhopeviral.NetizenUIutama;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenAdapter.ChatListAdapter;
import com.netizen.btsjhopeviral.NetizenModel.ChatMessage;
import com.netizen.btsjhopeviral.NetizenModel.ContactData;
import com.netizen.btsjhopeviral.NetizenModel.MessageSender;
import com.netizen.btsjhopeviral.NetizenModel.MessageState;
import com.netizen.btsjhopeviral.NetizenSave.SaveState;
import com.netizen.btsjhopeviral.NetizenUIutama.contact.NetizenEditContact;
import com.netizen.btsjhopeviral.NetizenUIutama.video.NetizenVideoActivityCall;
import com.netizen.btsjhopeviral.NetizenUIutama.video.NetizenVideoActivityReceive;
import com.netizen.btsjhopeviral.NetizenUtility.DialogCallback;
import com.netizen.btsjhopeviral.NetizenUtility.MessageDataDialog;
import com.netizen.btsjhopeviral.NetizenUtility.TextWatcherAdapter;
import com.netizen.btsjhopeviral.NetizenUtility.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class NetizenChatActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VIDEO = 2;
    private static int RESULT_LOAD_MUSIC = 3;
    String audioPath;
    public static String LINK_VIDEO = "";
    public static String LINK_VOICE = "";
    public static ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private CircleImageView contactImage;
    public static ChatListAdapter chatListAdapter;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1234;
    public static ContactData currentContactData;
    private MessageSender currentSender = MessageSender.SELF;

    public TextView contactName;
    private TextView contactState;

    private int changemode = 0;
    public static SaveState.Chat chat;

    public static final String EXTRA_IDENTIFIER = "fakechats.extra.id";
    String picturePath;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        dateFormat = android.text.format.DateFormat.getTimeFormat(this);

        ///hapus iklan inter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        SharedPreferences mykoin = this.getSharedPreferences("Mymode", Context.MODE_PRIVATE);
        changemode = mykoin.getInt("mode", changemode);

        int id = getIntent().getIntExtra(EXTRA_IDENTIFIER, -1);
        if (id == -1) {
            finish();
            return;
        }

        LINK_VIDEO = "android.resource://" + this.getPackageName() + "/" + R.raw.videonet;
        LINK_VOICE = "android.resource://" + this.getPackageName() + "/" + R.raw.voice_mail;

        SharedPreferences myvid = this.getSharedPreferences("Myvideo", Context.MODE_PRIVATE);
        LINK_VIDEO = myvid.getString("video", LINK_VIDEO);

        SharedPreferences mymp3 = this.getSharedPreferences("Mymp3", Context.MODE_PRIVATE);
        LINK_VOICE = myvid.getString("mp3", LINK_VOICE);

        chatMessages.add(new ChatMessage(getString(R.string.today), MessageSender.SYSTEM_DATE));

        ListView chatListView = findViewById(R.id.chat_list_view);
        chatListAdapter = new ChatListAdapter(this, chatMessages);
        chatListView.setAdapter(chatListAdapter);

        final EditText chatInputView = findViewById(R.id.chat_input_view);
        final ImageView chatButtonSend = findViewById(R.id.chat_button_send);
        chatButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(chatInputView.getText().toString(), currentSender);
                if (picturePath == null){
                    currentContactData = new ContactData(currentContactData.getName(), chatInputView.getText().toString(), currentContactData.getImageuser(),System.currentTimeMillis());
                    setCurrentContactData(currentContactData);
                    chat.data = currentContactData;
                    SaveState.save();
                } else {
                    currentContactData = new ContactData(currentContactData.getName().toString(),chatInputView.getText().toString(), picturePath,System.currentTimeMillis());
                    setCurrentContactData(currentContactData);
                    chat.data = currentContactData;
                    SaveState.save();

                }
                chatInputView.setText("");
            }
        });

        final ImageView sideSwitchButton = findViewById(R.id.sideSwitchButton);
        sideSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSender = currentSender.invert();
                if (currentSender == MessageSender.SELF)
                    sideSwitchButton.setImageResource(R.drawable.switcher_self);
                else if (currentSender == MessageSender.OTHER)
                    sideSwitchButton.setImageResource(R.drawable.switcher_other);
            }
        });

        final ImageView attachButton = findViewById(R.id.attachButton);
        final ImageView photoButton = findViewById(R.id.camButton);

        chatInputView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (chatInputView.getText().toString().trim().length() > 0) {
                    chatButtonSend.setImageResource(R.drawable.input_send);
                    attachButton.setVisibility(View.GONE);
                    photoButton.setVisibility(View.GONE);
                    sideSwitchButton.setVisibility(View.VISIBLE);
                } else {
                    chatButtonSend.setImageResource(R.drawable.input_mic_white);
                    attachButton.setVisibility(View.VISIBLE);
                    photoButton.setVisibility(View.VISIBLE);
                    sideSwitchButton.setVisibility(View.GONE);
                }
            }
        });

        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ChatMessage message = chatMessages.get(position);
                MessageDataDialog dialog = new MessageDataDialog(NetizenChatActivity.this);
                dialog.setMessageState(message.getMessageState());
                dialog.show(new DialogCallback<MessageState>() {
                    @Override
                    public void onResult(MessageState result) {
                        message.setMessageState(result);
                        chatListAdapter.notifyDataSetChanged();
                        SaveState.save();
                    }
                });
            }
        });

        initActionBar();

        //// Loading data ////
        System.out.println("Loading chat " + id);
        chat = SaveState.getChat(id);

        try {
            setCurrentContactData(chat.data);
            for (SaveState.Message msg : chat.messages) chatMessages.add(msg.chatMessage);

            if (chatListAdapter != null)
                chatListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            //To-do with expection
        }


    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    File imagePath;

    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/" + currentContactData.getName() + ".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }



    private void sendMessage(final String content, final MessageSender sender) {
        if (content.trim().length() == 0)
            return;

        final ChatMessage message = new ChatMessage(content, sender, MessageState.SEEN, System.currentTimeMillis());
        chatMessages.add(message);
        SaveState.createMessage(chat.id, message);

        if (chatListAdapter != null)
            chatListAdapter.notifyDataSetChanged();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            View customView = View.inflate(actionBar.getThemedContext(), R.layout.chat_action_bar, null);

            contactImage = customView.findViewById(R.id.contactImage);
            contactImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatMessages.clear();
                    chatListAdapter.notifyDataSetChanged();
                    finish();


                }
            });

            LinearLayout backButton = customView.findViewById(R.id.backButtonClickable);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatMessages.clear();
                    chatListAdapter.notifyDataSetChanged();
                    finish();

                }
            });
            contactName = customView.findViewById(R.id.actionbarTitle);
            contactState = customView.findViewById(R.id.actionbarSubtitle);
            currentContactData = new ContactData(contactName.getText().toString(), contactState.getText().toString(), "1",System.currentTimeMillis());
            LinearLayout contactDataButton = customView.findViewById(R.id.contactInfoClickable);
            contactDataButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NetizenChatActivity.this, NetizenEditContact.class);
                    startActivity(intent);

                }
            });

            actionBar.setCustomView(customView);

            Toolbar parent = (Toolbar) customView.getParent();
            parent.setPadding(0, 0, 0, 0);
            parent.setContentInsetsAbsolute(0, 0);
        }
    }

    public void setCurrentContactData(ContactData currentContactData) {
        NetizenChatActivity.currentContactData = currentContactData;
        contactName.setText(currentContactData.getName());
        contactState.setText(getString(R.string.last_seen_default)+" "+dateFormat.format(NetizenChatActivity.currentContactData.getTime()));
        try {
            if (currentContactData.getImageuser().equals("")) {
                Picasso.get().load(R.drawable.avatar_contact).centerCrop().fit().into(contactImage);
            } else {
                Picasso.get().load(new File(currentContactData.getImageuser())).centerCrop().fit().into(contactImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (chatMessages.get(0).getSender() == MessageSender.SYSTEM_UNKNOWN_NUMBER) {
            chatMessages.remove(0);
            chatListAdapter.notifyDataSetChanged();
        }
        if (Utils.isPhoneNumber(currentContactData.getName())) {
            chatMessages.add(0, new ChatMessage("", MessageSender.SYSTEM_UNKNOWN_NUMBER));
            chatListAdapter.notifyDataSetChanged();
        }
    }


    public void onDestroy() {
        super.onDestroy();
        chatMessages.clear();
        chatListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vidcall:
                if (changemode == 0) {
                    Intent intent2 = new Intent(NetizenChatActivity.this, NetizenVideoActivityCall.class);
                    startActivity(intent2);

                } else if (changemode == 1) {
                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onFinish() {


                            Intent intent = new Intent(NetizenChatActivity.this, NetizenVideoActivityReceive.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                    }.start();

                }

                return true;
//            case R.id.audiocall:
//
//                if (changemode == 0) {
//                    Intent intent = new Intent(NetizenChatActivity.this, NetizenCallActivity.class);
//                    startActivity(intent);
//                } else if (changemode == 1) {
//                    new CountDownTimer(10000, 1000) {
//                        @Override
//                        public void onFinish() {
//                            Intent intent = new Intent(NetizenChatActivity.this, NetizenCallActivityReceive.class);
//                            startActivity(intent);
//                        }
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                        }
//                    }.start();
//                }
//
//                return true;

            case R.id.mode:
                mode();
                return true;

            case R.id.vid:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_VIDEO);
                return true;

            case R.id.voice:
                Intent s = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(s, RESULT_LOAD_MUSIC);
                return true;
            case R.id.edit:
                Intent intent = new Intent(NetizenChatActivity.this, NetizenEditContact.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            contactImage.setImageBitmap(Utils.cropToCircle(BitmapFactory.decodeFile(picturePath)));
        }


        if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String videoPath = cursor.getString(columnIndex);
            cursor.close();
            LINK_VIDEO = videoPath;

            SharedPreferences myvid = getSharedPreferences("Myvideo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorvid = myvid.edit();
            editorvid.putString("video", LINK_VIDEO);
            editorvid.apply();


        }


        if (requestCode == RESULT_LOAD_MUSIC && resultCode == RESULT_OK && null != data) {
            Uri selectedAudio = data.getData();
            String[] filePathColumn = {MediaStore.Audio.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedAudio,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            audioPath = cursor.getString(columnIndex);
            cursor.close();
            LINK_VOICE = audioPath;

            SharedPreferences mymp3 = getSharedPreferences("Mymp3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editormp3 = mymp3.edit();
            editormp3.putString("mp3", LINK_VOICE);
            editormp3.apply();


        }


    }

    public void onResume() {
        super.onResume();
        SharedPreferences mykoin = this.getSharedPreferences("Mymode", Context.MODE_PRIVATE);
        changemode = mykoin.getInt("mode", changemode);
        contactName.setText(currentContactData.getName());
        contactState.setText(getString(R.string.last_seen_default)+" "+dateFormat.format(NetizenChatActivity.currentContactData.getTime()));
        if (currentContactData.getImageuser().equals("")) {
            contactImage.setImageBitmap(Utils.cropToCircle(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_contact)));

        } else {
            try {
                contactImage.setImageBitmap(Utils.cropToCircle(BitmapFactory.decodeFile(currentContactData.getImageuser())));
            } catch (Exception e) {
                contactImage.setImageBitmap(Utils.cropToCircle(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_contact)));

            }
        }
    }

    public void onBackPressed() {
        finish();
    }

    private void mode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NetizenChatActivity.this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.icon);
        builder.setTitle(getString(R.string.change_mode));
        builder.setMessage(getString(R.string.change_mode_description));
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(getString(R.string.receiver), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                changemode = 1;
                SharedPreferences mykoin = getSharedPreferences("Mymode", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorkoin = mykoin.edit();
                editorkoin.putInt("mode", changemode);
                editorkoin.apply();
                dialog.dismiss();

            }
        });

        builder.setNegativeButton(getString(R.string.caller), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                changemode = 0;
                SharedPreferences mykoin = getSharedPreferences("Mymode", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorkoin = mykoin.edit();
                editorkoin.putInt("mode", changemode);
                editorkoin.apply();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                } else {
                    Toast.makeText(NetizenChatActivity.this, "decline", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void ALERT (View view){
        Toast.makeText(NetizenChatActivity.this, getString(R.string.alert), Toast.LENGTH_LONG).show();
    }


}
