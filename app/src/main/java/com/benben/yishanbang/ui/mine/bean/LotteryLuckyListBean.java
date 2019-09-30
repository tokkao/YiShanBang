package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by lff
 * on 2019/8/26 0026
 * Describe:中奖名单
 */
public class LotteryLuckyListBean {

    /**
     * prizeGarde : 3
     * luckyUser : [{"id":"402881ef6bc55fe1016bc67825320003","mobile":"10010","nickname":"李易峰","avatar":"upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg"}]
     */

    private String prizeGarde;
    private List<LuckyUserBean> luckyUser;

    public String getPrizeGarde() {
        return prizeGarde;
    }

    public void setPrizeGarde(String prizeGarde) {
        this.prizeGarde = prizeGarde;
    }

    public List<LuckyUserBean> getLuckyUser() {
        return luckyUser;
    }

    public void setLuckyUser(List<LuckyUserBean> luckyUser) {
        this.luckyUser = luckyUser;
    }

    public static class LuckyUserBean {
        /**
         * id : 402881ef6bc55fe1016bc67825320003
         * mobile : 10010
         * nickname : 李易峰
         * avatar : upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg
         */

        private String id;
        private String mobile;
        private String nickname;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}

