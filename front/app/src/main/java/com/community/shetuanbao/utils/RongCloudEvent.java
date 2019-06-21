package com.community.shetuanbao.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.community.shetuanbao.community.CommunityStaffActivity;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;

public class RongCloudEvent implements RongIM.ConversationBehaviorListener,
        RongIMClient.OnReceiveMessageListener,
        RongIM.ConversationListBehaviorListener,
        RongIM.UserInfoProvider,
        RongIM.GroupInfoProvider,
        RongIM.LocationProvider {
    private static RongCloudEvent mRongCloudInstance;//创建一个融云单实例

    private Context mContext;

    /**
     * 閸掓繂顫愰崠锟絉ongCloud.
     *
     * @param context 娑撳﹣绗呴弬鍥ワ拷
     */
    public static void init(Context context) {///初始化融云

        if (mRongCloudInstance == null) {

            synchronized (RongCloudEvent.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudEvent(context);
                }
            }
        }

    }


    public RongCloudEvent(Context mContext) {
        this.mContext = mContext;
        //初始化融云监听器
        initListener();

    }

    /**
     * init 閸氬骸姘ㄩ懗鍊燁啎缂冾喚娈戦惄鎴濇儔
     */
    private void initListener() {
        RongIM.setConversationBehaviorListener(this);//鐠佸墽鐤嗘导姘崇樈閻ｅ矂娼伴幙宥勭稊閻ㄥ嫮娲冮崥顒�娅掗妴锟�

    }

    /**
     * 閼惧嘲褰嘡ongCloud 鐎圭偘绶ラ妴锟�
     *
     * @return RongCloud閵嗭拷
     */
    public static RongCloudEvent getInstance() {
        return mRongCloudInstance;
    }


    /**
     * 娴兼俺鐦介悾宀勬桨閻愮懓鍤☉鍫熶紖閻ㄥ嫮娲冮崥锟�
     *
     * @param context
     * @param view
     * @param message
     * @return
     */
    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        if (message.getContent() instanceof ImageMessage) { //鐎圭偟骞囨导姘崇樈閻ｅ矂娼伴悙鐟板毊閺屻儳婀呮径褍娴橀柅鏄忕帆  娓氭繆绂� PhotoActivity 閸滐拷閸忚泛绔风仦锟芥禒銉ュ挤 menu/de_fix_username.xml
            ImageMessage imageMessage = (ImageMessage) message.getContent();
            Intent intent = new Intent(context, CommunityStaffActivity.class);
            intent.putExtra("photo", imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri());
            if (imageMessage.getThumUri() != null)
                intent.putExtra("thumbnail", imageMessage.getThumUri());
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }


    @Override
    public void onStartLocation(Context arg0, LocationCallback arg1) {
        // TODO Auto-generated method stub

    }


    @Override
    public Group getGroupInfo(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public UserInfo getUserInfo(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean onConversationClick(Context arg0, View arg1,
                                       UIConversation arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onConversationLongClick(Context arg0, View arg1,
                                           UIConversation arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onConversationPortraitClick(Context arg0,
                                               Conversation.ConversationType arg1, String arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onConversationPortraitLongClick(Context arg0,
                                                   Conversation.ConversationType arg1, String arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onReceived(Message arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onMessageLongClick(Context arg0, View arg1, Message arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onUserPortraitClick(Context arg0, Conversation.ConversationType arg1,
                                       UserInfo arg2) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onUserPortraitLongClick(Context arg0, Conversation.ConversationType arg1,
                                           UserInfo arg2) {
        // TODO Auto-generated method stub
        return false;
    }

}