package com.anibalbastias.android.marvelapp.base.api.data.marvel

import android.support.annotation.StringDef

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MarvelAPIGSONManager private constructor() {

    @StringDef(
            SECTION_HOME_HEADER,
            SECTION_TAB_LIST,
            SECTION_MEDIA,
            SECTION_HEADER,
            SECTION_BASIC_INFO,
            SECTION_LABEL_VALUE_LIST,
            SECTION_COMMENTS,
            SECTION_FEATURE_SPEC_TAB,
            SECTION_DISCLAIMERS,
            SECTION_SELLER_INFO,
            SECTION_SELLER_LOCATION_INFO,
            SECTION_AD,
            SECTION_DOUBLE_CLICK_AD,
            SECTION_TABBED_SECTION_LIST,
            SECTION_RED_BOOK_REPORT_LINK,
            SECTION_RELATED_EDITORIAL,
            SECTION_REPORT_AD,
            SECTION_FEATURES,
            SECTION_EXTERNAL_DRIVER,
            SECTION_SPECS,
            SECTION_SPECIAL_OFFER_LIST,
            SECTION_IN_HOUSE_ADS,
            SECTION_TEXT_LIST,
            SECTION_REPAYMENT,
            SECTION_SINGLE_STOCK_ITEM,
            SECTION_THANK_YOU,
            SECTION_RELATED_STOCKS,
            TAB_ITEM_BODY_STYLE,
            TAB_ITEM_LOGO
    )

    @Retention(AnnotationRetention.SOURCE)
    annotation class SectionType

    @StringDef(
            ACTION_SHOW_LISTING,
            ACTION_POPUP_TEXT,
            ACTION_DISPLAY_PRICE,
            ACTION_SHOW_ITEM_DETAILS,
            ACTION_NEW_CAR,
            ACTION_WEB_LINK,
            ACTION_ENQUIRE_NOW,
            ACTION_SHOW_REFINEMENTS,
            ACTION_SCROLL_TO_FEATURES,
            ACTION_SELL_MY_STOCK
    )

    @Retention(AnnotationRetention.SOURCE)
    annotation class ActionType


    private fun createGson(): Gson {
        val gsonBuilder: GsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

//        gsonBuilder.registerTypeAdapter(Section::class.java, RetailSectionDeserializer())
//        gsonBuilder.registerTypeAdapter(BaseActionData::class.java, RetailActionDeserializer())
//        gsonBuilder.registerTypeAdapter(ControlData::class.java, RetailControlDeserializer())

        return gsonBuilder
    }

    companion object {
        private var instance: MarvelAPIGSONManager? = null

        //SECTION TYPE
        const val SECTION_HOME_HEADER = "homeHeader"
        const val SECTION_TAB_LIST = "tabList"
        const val SECTION_MEDIA = "media"
        const val SECTION_HEADER = "header"
        const val SECTION_BASIC_INFO = "basicInfoIconTextList"
        const val SECTION_LABEL_VALUE_LIST = "labelValueList"
        const val SECTION_COMMENTS = "comments"
        const val SECTION_FEATURE_SPEC_TAB = "featuresAndSpecsTab"
        const val SECTION_DISCLAIMERS = "disclaimers"
        const val SECTION_SELLER_INFO = "sellerInfo"
        const val SECTION_SELLER_LOCATION_INFO = "sellerLocationInfo"
        const val SECTION_AD = "ad"
        const val SECTION_DOUBLE_CLICK_AD = "doubleClickAd"
        const val SECTION_TABBED_SECTION_LIST = "tabbedSectionList"
        const val SECTION_RED_BOOK_REPORT_LINK = "redbookReportLink"
        const val SECTION_RELATED_EDITORIAL = "relatedEditorial"
        const val SECTION_WEB_LINK = "webLink"
        const val SECTION_REPORT_AD = "reportAd"
        const val SECTION_FEATURES = "features"
        const val SECTION_EXTERNAL_DRIVER = "externalDriver"
        const val SECTION_SPECS = "specs"
        const val SECTION_SPECIAL_OFFER_LIST = "specialOfferList"
        const val SECTION_IN_HOUSE_ADS = "inHouseAds"
        const val SECTION_TEXT_LIST = "textList"
        const val SECTION_REPAYMENT = "repayment"
        const val SECTION_SINGLE_STOCK_ITEM = "singleStockItem"
        const val TAB_ITEM_BODY_STYLE = "bodyStyle"
        const val TAB_ITEM_LOGO = "logo"
        const val SECTION_NATIVE_CONTENT_CARDS = "nativeContentCards"

        //Enquiry Form
        const val SECTION_FORM_CHECKBOX_LIST = "formCheckboxList"
        const val SECTION_FORM_MESSAGE = "formMessage"
        const val SECTION_FORM_CONTACT_DETAILS = "formContactDetails"
        const val SECTION_FORM_BUTTON = "formButton"
        const val SECTION_THANK_YOU = "thankYou"
        const val SECTION_PROXIMITY_CHECK = "proximityCheck"
        const val SECTION_RELATED_STOCKS = "relatedStocks"

        //Enquiry Form Bnca
        const val SECTION_ENQUIRY_DEALER_LIST = "enquiryDealerList"
        const val SECTION_ENQUIRY_BNCA_ACTION = "bncaEnquiryAction"
        const val SECTION_ENQUIRY_CONTACT_SELLER_DEALER = "contactSellerDealer"
        const val SECTION_ENQUIRY_CONTACT_PRIVATE_SELLER = "contactSellerPrivate"
        const val SECTION_ENQUIRY_BNCA_POST_CODE = "bncaPostcode"

        //Enquiry Control
        const val CONTROL_TEXT_AREA = "textarea"
        const val CONTROL_TEXT_BOX = "textbox"
        const val CONTROL_CHECKBOX = "checkbox"
        const val CONTROL_BUTTON = "button"

        //Enquiry Api Names
        const val ENQUIRY_ENDPOINT_FOR_FORM = "enquiry"
        const val ENQUIRY_ENDPOINT_FOR_THANKYOU = "thankyou"

        //Editorial specific
        const val SECTION_E_HERO = "hero"
        const val SECTION_E_RATING = "rating"
        const val SECTION_E_BULLETED_LIST = "bulletedList"
        const val SECTION_E_HTML = "html"
        const val SECTION_E_SINGLE_IMAGE = "singleImage"
        const val SECTION_E_SINGLE_VIDEO = "singleVideo"
        const val SECTION_E_AUTHOR = "author"
        const val SECTION_E_ALSO_CONSIDER = "alsoConsider"
        const val SECTION_E_QUOTE = "quote"
        const val SECTION_E_HEADER = "editorialHeader"
        const val SECTION_E_SUB_HEADING = "editorialSubHeading"
        const val SECTION_E_SUMMARY = "editorialSummary"
        const val SECTION_CONTENT_HEADING = "contentHeading"
        const val SECTION_E_COMMENTS = "commentsLink"
        const val SECTION_YOUTUBE = "youTube"

        //Action Type
        const val ACTION_SHOW_LISTING = "showListing"
        const val ACTION_POPUP_TEXT = "popupText"
        const val ACTION_DISPLAY_PRICE = "onRoadCosts"//for DisplayPriceAction
        const val ACTION_SHOW_ITEM_DETAILS = "showItemDetails"
        const val ACTION_NEW_CAR = "newCar"
        const val ACTION_WEB_LINK = "webLink"
        const val ACTION_ENQUIRE_NOW = "enquireNow"
        const val ACTION_ENQUIRE_BNCA = "goToSubmitEnquiryBnca"
        const val ACTION_PRIVATE_SELLER_NUMBER = "showPrivateSellerNumber"
        const val ACTION_SHOW_VERIFY_CODE = "showVerifyCode"
        const val ACTION_SHOW_REFINEMENTS = "showRefinements"
        const val ACTION_SCROLL_TO_FEATURES = "scrollToFeatures"
        const val ACTION_SELL_MY_STOCK = "sellMyStock"
        const val ACTION_DEALER_CALL = "dealerCall"
        const val ACTION_DEALER_SMS = "dealerSms"
        const val ACTION_SAVE_ITEM = "saveItem"

        fun create(): Gson {
            if (instance == null) {
                instance = MarvelAPIGSONManager()
            }
            return instance!!.createGson()
        }

        fun createGsonBuilder(): GsonBuilder {
            if (instance == null) {
                instance = MarvelAPIGSONManager()
            }
            return instance!!.createGsonBuilder()
        }
    }
}