package com.stb.model;

import javax.persistence.*;

public class Community {
    @Id
    @Column(name = "community_id")
    private Integer communityId;

    @Column(name = "community_name")
    private String communityName;

    @Column(name = "community_introduce")
    private String communityIntroduce;

    @Column(name = "community_kouhao")
    private String communityKouhao;

    @Column(name = "community_tubiao")
    private String communityTubiao;

    @Column(name = "community_picture")
    private String communityPicture;

    @Column(name = "community_stat")
    private String communityStat;

    /**
     * @return community_id
     */
    public Integer getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId
     */
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    /**
     * @return community_name
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * @param communityName
     */
    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    /**
     * @return community_introduce
     */
    public String getCommunityIntroduce() {
        return communityIntroduce;
    }

    /**
     * @param communityIntroduce
     */
    public void setCommunityIntroduce(String communityIntroduce) {
        this.communityIntroduce = communityIntroduce;
    }

    /**
     * @return community_kouhao
     */
    public String getCommunityKouhao() {
        return communityKouhao;
    }

    /**
     * @param communityKouhao
     */
    public void setCommunityKouhao(String communityKouhao) {
        this.communityKouhao = communityKouhao;
    }

    /**
     * @return community_tubiao
     */
    public String getCommunityTubiao() {
        return communityTubiao;
    }

    /**
     * @param communityTubiao
     */
    public void setCommunityTubiao(String communityTubiao) {
        this.communityTubiao = communityTubiao;
    }

    /**
     * @return community_picture
     */
    public String getCommunityPicture() {
        return communityPicture;
    }

    /**
     * @param communityPicture
     */
    public void setCommunityPicture(String communityPicture) {
        this.communityPicture = communityPicture;
    }

    /**
     * @return community_stat
     */
    public String getCommunityStat() {
        return communityStat;
    }

    /**
     * @param communityStat
     */
    public void setCommunityStat(String communityStat) {
        this.communityStat = communityStat;
    }
}