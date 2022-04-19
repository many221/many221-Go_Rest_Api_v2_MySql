package com.gr_20.Api.models;

public class PostModel {
    private int id;
    private int user_id;

    private String title;
    private String body;

    public PostModel() {
    }

    public PostModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", userId=" + user_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    /*
 {
id: 1749,
user_id: 3661,
title: "Ara thorax vox aptus claustrum sto viscus.",
body: "Pecto admoveo averto. Avaritia numquam cena. Verumtamen desipio defungo. Appono ambulo subiungo. Vesper cicuta tot. Mollitia amiculum depromo. Alii deleo tui. Aut soluta vis. Vero est sublime. Tabella creta adeo. Qui arto dolorum. Arma cedo spero. Temporibus non quia. Illum nesciunt sordeo. Alveus vetus aliqua. Abscido cerno thymum. Defungo eaque viscus. Vito vitae degenero. Facere ubi vulariter."
},
 */
}
