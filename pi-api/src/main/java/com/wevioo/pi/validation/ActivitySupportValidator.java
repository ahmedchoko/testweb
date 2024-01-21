package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.rest.dto.request.ActivitySupportForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActivitySupportValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return ActivitySupportForPostDto.class.equals(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object o, Errors errors) {
        /**
         * overrided method
         */
    }

    /**
     * Validate Operator for post method
     *
     * @param activitySupportForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
      public static    void validatePost(ActivitySupportForPostDto activitySupportForPostDto, Errors errors ,    String fieldName , Integer i , List<Long> paramValueLongList) {

          if(activitySupportForPostDto == null){
              errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE,
                      ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
          }


        if (CommonUtilities.isNull(activitySupportForPostDto.getTypeActivitySupportId())) {
            errors.rejectValue(fieldName + "[" + i +"].typeActivitySupportId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }else {


            if(paramValueLongList.contains(activitySupportForPostDto.getTypeActivitySupportId())) {
                if (CommonUtilities.isNull(activitySupportForPostDto.getIssuingAuthorityId())) {
                    errors.rejectValue(fieldName + "[" + i + "].issuingAuthorityId", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
                }
            }

        }


    }

    /**
     * validate Post Activity  SupportForPostDto
     * @param activitySupportForPostDto
     * @param errors
     * @param isMainActivity
     */
    public static void  validateActivitySupportPost(List<ActivitySupportForPostDto> activitySupportForPostDto,
                                                    Errors errors ,
                                                    boolean isMainActivity ,
                                                    String fieldName,
                                                    List<Long> paramValueLongList){


        if(isMainActivity){
            if( activitySupportForPostDto== null || activitySupportForPostDto.isEmpty() ) {
                errors.rejectValue(fieldName, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }else {
                for ( int i  =0 ; i< activitySupportForPostDto.size()  ; i++) {
                    validatePost( activitySupportForPostDto.get(i), errors ,fieldName , i ,paramValueLongList);
                }
            }
        }



    }





}
