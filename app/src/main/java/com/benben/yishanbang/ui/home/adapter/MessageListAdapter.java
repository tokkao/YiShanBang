package com.benben.yishanbang.ui.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.adapter.AFinalRecyclerViewAdapter;
import com.benben.yishanbang.adapter.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.utils.TimeFormat;

/**
 * Author:zhn
 * Time:2019/5/13 0013 16:36
 * <p>
 * 消息列表Adapter
 */
public class MessageListAdapter extends AFinalRecyclerViewAdapter<Conversation> {

    public MessageListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MessageListViewHolder(m_Inflater.inflate(R.layout.item_message_list, parent,
                false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((MessageListViewHolder) holder).setContent(position, getItem(position));
    }

    class MessageListViewHolder extends BaseRecyclerViewHolder {

        View rootView;
        CircleImageView civMessageAvatar;
        TextView tvMessageTitle;
        TextView tvMessageInfo;
        TextView tvMessageCount;


        public MessageListViewHolder(View view) {
            super(view);
            rootView = view;
            civMessageAvatar = view.findViewById(R.id.civ_message_avatar);
            tvMessageTitle = view.findViewById(R.id.tv_message_title);
            tvMessageInfo = view.findViewById(R.id.tv_message_info);
            tvMessageCount = view.findViewById(R.id.tv_message_count);
        }

        public void setContent(int position, Conversation data) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(rootView, position, data);
                    }
                }
            });
            if (position == 0) {
                civMessageAvatar.setImageResource(R.mipmap.icon_system_message);
            } else if (position == 1) {
                civMessageAvatar.setImageResource(R.mipmap.icon_customer_service);
            } else {
                Glide.with(m_Context).load(data.getAvatarFile()).into(civMessageAvatar);
//                ImageUtils.getPic( , civMessageAvatar, m_Context, R.mipmap.icon_default_avatar);

            }
            if (data.getUnReadMsgCnt() > 0 && data.getUnReadMsgCnt() < 99) {
                tvMessageCount.setVisibility(View.VISIBLE);
                tvMessageCount.setText("" + data.getUnReadMsgCnt());
            } else if (data.getUnReadMsgCnt() > 99) {
                tvMessageCount.setVisibility(View.VISIBLE);
                tvMessageCount.setText("99+");
            } else {
                tvMessageCount.setVisibility(View.GONE);
            }
            tvMessageTitle.setText(data.getTitle());
            Message lastMsg = data.getLatestMessage();
            if (lastMsg != null) {
                TimeFormat timeFormat = new TimeFormat(m_Context, lastMsg.getCreateTime());
                //会话界面时间
//                tvMessageTime.setText(timeFormat.getTime());
                tvMessageTitle.setText(data.getTitle());

                if (data.getType().equals(ConversationType.single)) {
                    UserInfo mUserInfo = (UserInfo) data.getTargetInfo();
                    if (mUserInfo != null) {
                        mUserInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int status, String desc, Bitmap bitmap) {
                                if (status == 0) {
                                    civMessageAvatar.setImageBitmap(bitmap);
                                } else {
                                    civMessageAvatar.setImageResource(R.mipmap.icon_default_avatar);
                                }
                            }
                        });
                    } else {
                        civMessageAvatar.setImageResource(R.mipmap.icon_default_avatar);
                    }
                } else if (data.getType().equals(ConversationType.group)) {
                    GroupInfo mGroupInfo = (GroupInfo) data.getTargetInfo();

                    if (mGroupInfo != null) {
                        mGroupInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int status, String s, Bitmap bitmap) {
                                if (status == 0) {
                                    civMessageAvatar.setImageBitmap(bitmap);
                                } else {
                                    civMessageAvatar.setImageResource(R.mipmap.icon_default_avatar);
                                }
                            }
                        });
                    } else {
                        civMessageAvatar.setImageResource(R.mipmap.icon_default_avatar);
                    }
                }

                String contentStr;
                switch (lastMsg.getContentType()) {
                    case image:
                        contentStr = m_Context.getString(R.string.type_picture);
                        break;
                    case voice:
                        contentStr = m_Context.getString(R.string.type_voice);
                        break;
                    case location:
                        contentStr = m_Context.getString(R.string.type_location);
                        break;
                    case file:
                        String extra = lastMsg.getContent().getStringExtra("video");
                        if (!TextUtils.isEmpty(extra)) {
                            contentStr = m_Context.getString(R.string.type_smallvideo);
                        } else {
                            contentStr = m_Context.getString(R.string.type_file);
                        }
                        break;
                    case video:
                        contentStr = m_Context.getString(R.string.type_video);
                        break;
                    case eventNotification:
                        contentStr = m_Context.getString(R.string.group_notification);
                        break;
                    case custom:
                        CustomContent customContent = (CustomContent) lastMsg.getContent();
                        Boolean isBlackListHint = customContent.getBooleanValue("blackList");
                        if (isBlackListHint != null && isBlackListHint) {
                            contentStr = m_Context.getString(R.string.jmui_server_803008);
                        } else {
                            contentStr = m_Context.getString(R.string.type_custom);
                        }
                        break;
                    case prompt:
                        contentStr = ((PromptContent) lastMsg.getContent()).getPromptText();
                        break;
                    default:
                        contentStr = ((TextContent) lastMsg.getContent()).getText();
                        break;
                }
                MessageContent msgContent = lastMsg.getContent();
                if (lastMsg.getTargetType() == ConversationType.group && !contentStr.equals("[群成员变动]")) {
                    UserInfo info = lastMsg.getFromUser();
                    String fromName = info.getDisplayName();
                    if (msgContent.getContentType() == ContentType.prompt) {
                        tvMessageInfo.setText(contentStr);
                    } else if (info.getUserName().equals(JMessageClient.getMyInfo().getUserName())) {
                        tvMessageInfo.setText(contentStr);
                    } else {
                        tvMessageInfo.setText(fromName + ": " + contentStr);
                    }
                } else {
                    if (lastMsg.getUnreceiptCnt() == 0) {
                        if (lastMsg.getTargetType().equals(ConversationType.single) &&
                                lastMsg.getDirect().equals(MessageDirect.send) &&
                                !lastMsg.getContentType().equals(ContentType.prompt) &&
                                //排除自己给自己发送消息
                                !((UserInfo) lastMsg.getTargetInfo()).getUserName().equals(JMessageClient.getMyInfo().getUserName())) {
                            if (lastMsg.getStatus() == MessageStatus.send_fail) {
                                tvMessageInfo.setText(contentStr);
                            } else {
                                tvMessageInfo.setText("[已读]" + contentStr);
                            }
                        } else {
                            tvMessageInfo.setText(contentStr);
                        }
                    } else {
                        if (lastMsg.getTargetType().equals(ConversationType.single) &&
                                lastMsg.getDirect().equals(MessageDirect.send) &&
                                !lastMsg.getContentType().equals(ContentType.prompt) &&
                                !((UserInfo) lastMsg.getTargetInfo()).getUserName().equals(JMessageClient.getMyInfo().getUserName())) {
                            contentStr = "[未读]" + contentStr;
                            SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
                            builder.setSpan(new ForegroundColorSpan(m_Context.getResources().getColor(R.color.line_normal)),
                                    0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvMessageInfo.setText(builder);
                        } else {
                            tvMessageInfo.setText(contentStr);
                        }

                    }
                }
            } else {
                //会话列表时间展示的是最后一条会话,那么如果最后一条消息是空的话就不显示
              //  tvMessageTime.setText("");
                tvMessageInfo.setText("");
            }
        }
    }

}

