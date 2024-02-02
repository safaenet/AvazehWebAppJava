package com.safadana.AvazehRetailManagement.SecurityAndSettingsModels;

import lombok.Data;

import java.util.List;

import com.safadana.AvazehRetailManagement.Models.UserDescriptionModel;

@Data
public class PrintSettingsModel {
    private String mainHeaderText = "فروشگاه آوازه";
    private String headerDescription1 = "کرکره برقی، جک پارکینگی، دزدگیر اماکن";
    private String headerDescription2 = "01734430827 - 09357330303";
    private String leftHeaderImage = "\\Images\\LeftImage.png";
    private String rightHeaderImage = "\\Images\\RightImage.png";
    private List<UserDescriptionModel> userDescriptions;
    private String footerTextLeft = "کیفیت برتر، قیمت مناسب";
    private String footerTextRight = "توسعه دهنده نرم افزار: صفا دانا";

    private int mainHeaderTextFontSizeA5P = 30;
    private int headerDescriptionFontSizeA5P = 10;
    private int typeTextFontSizeA5P = 16; //پیش فاکتور، فاکتور فروش، جزئیات فایل

    private int mainHeaderTextFontSizeA5L = 30;
    private int headerDescriptionFontSizeA5L = 10;
    private int typeTextFontSizeA5L = 14; //پیش فاکتور، فاکتور فروش، جزئیات فایل

    private int mainHeaderTextFontSizeA4P = 30;
    private int headerDescriptionFontSizeA4P = 10;
    private int typeTextFontSizeA4P = 14; //پیش فاکتور، فاکتور فروش، جزئیات فایل

    private int pageHeaderFontSize = 10;
    private int detailsFontSize = 10;
    private int pageFooterFontSize = 10;
    private int descriptionFontSize = 14;

    private String defaultPrintLayout = "عمودی"; //Landscape, Portrait
    private String defaultPaperSize = "A5"; //A5, A4
}
