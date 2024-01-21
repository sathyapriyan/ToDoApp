package com.example.todoapp.core.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 21.December.2022:16:50

///////////////////////////////////////////////////////////////////////////////////////////////

object PreferencesKeys {

    // 0 - Project 1 - Product
    val APP_TYPE = intPreferencesKey("app_type")

    val LAST_CONNECTED_DEVICE_NAME = stringPreferencesKey("last_connected_device_name")
    val LAST_CONNECTED_DEVICE_MAC_ADDRESS = stringPreferencesKey("last_connected_device_mac_address")
    val LAST_CONNECTED_PRINTER_MAC_ADDRESS = stringPreferencesKey("last_connected_printer_mac_address")
    val SELECTED_SYSTEM_TYPE = intPreferencesKey("selected_system_type")
    val SELECTED_CT_TYPE = intPreferencesKey("selected_ct_type")
    val SELECTED_CALIBRATION_TYPE = intPreferencesKey("selected_calibration_type")
    val SELECTED_PULSE_OUTPUT = intPreferencesKey("selected_pulse_output")
    val CURRENTLY_SELECTED_METER_NUMBER = stringPreferencesKey("currently_selected_meter_number")
    val SCREEN_HAS_SUMMARY = booleanPreferencesKey("screen_has_summary")
    val SHOW_ALL_RECORDS = booleanPreferencesKey("show_all_records")
    val LAZY_COLUMN_ITEM_POSITION = intPreferencesKey("lazy_column_item_position")
    val SELECTED_CALIBRATION_INDEX = intPreferencesKey("selected_calibration_position")
    val CALIBRATION_DATE = stringPreferencesKey("calibration_date")
    val DUE_DATE = stringPreferencesKey("due_date")
    val TPRS_SERIAL_NUMBER = stringPreferencesKey("tprs_serial_number")
    val PRINTER_TYPE = stringPreferencesKey("printer_type")
    val PRINTER_SIZE = stringPreferencesKey("printer_size")
    val SELECTED_METER_CLASS = stringPreferencesKey("selected_meter_class")

    val LOCAL_BCS_IP_ADDRESS = stringPreferencesKey("local_bcs_ip_address")

    val CONSUMER_NAME_TYPE = intPreferencesKey("consumer_name_type")
    val PHASE_TYPE  = intPreferencesKey("phase_type")
    val SIGNATURE_1  = intPreferencesKey("Signature_1")
    val SIGNATURE_2  = intPreferencesKey("Signature_2")
    val SIGNATURE_3  = intPreferencesKey("Signature_3")
    val SIGNATURE_4  = intPreferencesKey("Signature_4")
    val SIGNATURE_5  = intPreferencesKey("Signature_5")
    val SIGNATURE_6  = intPreferencesKey("Signature_6")
    val SIGNATURE_7  = intPreferencesKey("Signature_7")

}