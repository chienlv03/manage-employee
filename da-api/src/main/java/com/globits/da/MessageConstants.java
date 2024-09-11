package com.globits.da;

public class MessageConstants {
    public static final String NOT_VALID = "Email is not valid!";
    public static final String PHONE_REGEX = "^[0-9]{1,11}$";
    public static final String CODE_REGEX = "^[\\S]{6,10}$";
    public static final String COMMUNE_NOT_BELONG_TO_DISTRICT = "Commune does not belong to the specified District!";
    public static final String DISTRICT_NOT_BELONG_TO_PROVINCE = "District does not belong to the specified Province!";
    public static final String AGE_VALID = "Age must be greater than 0";
    public static final String PROVINCE_NOT_EXITED = "Province does not exist.";
    public static final String DISTRICT_NOT_EXITED = "District does not exist.";
    public static final String COMMUNE_NOT_EXITED = "Commune does not exist.";
    public static final String EMPLOYEE_NOT_EXITED = "Employee does not exist.";
    public static final String PROVINCE_NAME_EXITED = "Province name exited";
    public static final String CODE_VALID = "Code must be between 6 and 10 characters and not contain spaces";
    public static final String PHONE_VALID = "Phone numbers must not exceed 11 digits";
    public static final String REQUIRED_CODE = "Code is required";
    public static final String REQUIRED_NAME = "Name is required";
    public static final String REQUIRED_EMAIL = "Email is required";
    public static final String REQUIRED_PHONE = "Phone is required";
    public static final String REQUIRED_AGE = "Age is required";
    public static final String REQUIRED_PROVINCE = "Province is required";
    public static final String REQUIRED_DISTRICT = "District is required";
    public static final String REQUIRED_COMMUNE = "Commune is required";

    public static final String EMPLOYEE_CANNOT_HAVE_MORE_THAN_3_CERTIFICATES = "Employees cannot have more than 3 certificates of the same type that are still valid";
    public static final String EMPLOYEE_ALREADY_HAS_DIPLOMA = "Employee already has a valid diploma of this type from the same province";
    public static final String CODE_UNIQUE = "Code must be unique";
    public static final String DISTRICT_NAME_EXITED = "District name exited";
    public static final String FAILED_EXPORT_EXCEL = "Failed to export excel file";

    public static final String DELETE_SUCCESS = "Delete successfully";
    public static final String DIPLOMA_NOT_FOUND = "Diploma not found";
    public static final String IMPORT_SUCCESS = "Import successfully";
    public static final String VALIDATE_DATE = "Start date mst be before now and end date must be after now";

}
