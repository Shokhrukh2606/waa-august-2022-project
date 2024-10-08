package com.example.backend.domain;

import java.util.Arrays;

public enum State {

    ALABAMA("ALABAMA"),
    ALASKA("ALASKA"),
    ARIZONA("ARIZONA"),
    ARKANSAS("ARKANSAS"),
    CALIFORNIA("CALIFORNIA"),
    COLORADO("COLORADO"),
    CONNECTICUT("CONNECTICUT"),
    DELAWARE("DELAWARE"),
    FLORIDA("FLORIDA"),
    GEORGIA("GEORGIA"),
    HAWAII("HAWAII"),
    IDAHO("IDAHO"),
    ILLINOIS("ILLINOIS"),
    INDIANA("INDIANA"),
    IOWA("IOWA"),
    KANSAS("KANSAS"),
    KENTUCKY("KENTUCKY"),
    LOUISIANA("LOUISIANA"),
    MAINE("MAINE"),
    MARYLAND("MARYLAND"),
    MASSACHUSETTS("MASSACHUSETTS"),
    MICHIGAN("MICHIGAN"),
    MINNESOTA("MINNESOTA"),
    MISSISSIPPI("MISSISSIPPI"),
    MISSOURI("MISSOURI"),
    MONTANA("MONTANA"),
    NEBRASKA("NEBRASKA"),
    NEVADA("NEVADA"),
    NEW_HAMPSHIRE("NEW HAMPSHIRE"),
    NEW_JERSEY("NEW JERSEY"),
    NEW_MEXICO("NEW MEXICO"),
    NEW_YORK("NEW YORK"),
    NORTH_CAROLINA("NORTH CAROLINA"),
    NORTH_DAKOTA("NORTH DAKOTA"),
    OHIO("OHIO"),
    OKLAHOMA("OKLAHOMA"),
    OREGON("OREGON"),
    PENNSYLVANIA("PENNSYLVANIA"),
    RHODE_ISLAND("RHODE ISLAND"),
    SOUTH_CAROLINA("SOUTH CAROLINA"),
    SOUTH_DAKOTA("SOUTH DAKOTA"),
    TENNESSEE("TENNESSEE"),
    TEXAS("TEXAS"),
    UTAH("UTAH"),
    VERMONT("VERMONT"),
    VIRGINIA("VIRGINIA"),
    WASHINGTON("WASHINGTON"),
    WEST_VIRGINIA("WEST VIRGINIA"),
    WISCONSIN("WISCONSIN"),
    WYOMING("WYOMING");

    final String title;

    State(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static State getByTitle(String title) {
        return Arrays.stream(values()).filter(e -> e.getTitle().equalsIgnoreCase(title)).findAny().orElseThrow(NoSuchFieldError::new);
    }
}
