package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Participation Identification Mapper
 */
@Mapper(componentModel = "spring" , uses = {})
public interface ParticipationIdentificationMapper {

    /**
     * Maps a ParticipationIdentificationStepTwoForPostDto to a ParticipationIdentification entity.
     *
     * @param dto The ParticipationIdentificationStepTwoForPostDto to map.
     * @return The ParticipationIdentification entity representing the mapped ParticipationIdentification.
     */
    ParticipationIdentification toEntity(ParticipationIdentificationStepTwoForPostDto dto);



    /**
     * Converts a ParticipationIdentification object to a ParticipationIdentificationStepTwoForGetDto object.
     *
     * @param directInvestRequest The ParticipationIdentification object to be converted.
     * @return The converted ParticipationIdentificationStepTwoForGetDto object.
     */
    @Mapping( target = "ficheInvestId" , source =  "id")
    @Mapping( target = "freeCapital" , source =  "participationIdentification.freeCapital")
    @Mapping( target = "socialCapital" , source =  "participationIdentification.socialCapital")
    @Mapping( target = "immatriculationDate" , source =  "participationIdentification.immatriculationDate")
    @Mapping( target = "participationRate" , source =  "participationIdentification.participationRate")
    @Mapping( target = "numberAction" , source =  "participationIdentification.numberAction")
    @Mapping( target = "nominalValue" , source =  "participationIdentification.nominalValue")
    @Mapping(target = "id" ,  source =  "participationIdentification.id" )
    @Mapping(target = "numberPart" ,  source =  "participationIdentification.numberPart" )
    @Mapping(target = "contributionAmount" , source = "participationIdentification.contributionAmount")
    @Mapping(target = "paidCapitalByTranche" , source = "participationIdentification.paidCapitalByTranche")
    @Mapping(target = "numberActionAcquired" ,  source =  "participationIdentification.numberActionAcquired" )
    @Mapping(target = "unitAcquisitionCost" ,  source =  "participationIdentification.unitAcquisitionCost" )
    @Mapping(target = "totalAmountAcquisition" , source = "participationIdentification.totalAmountAcquisition")
    @Mapping(target = "transactionNoticeDate" , source = "participationIdentification.transactionNoticeDate")
    @Mapping(target = "acquisitionContractRegistrationDate" , source = "participationIdentification.acquisitionContractRegistrationDate")
    @Mapping(target = "registrationCertificateDate" , source = "participationIdentification.registrationCertificateDate")
    @Mapping(target = "totalAmount" , source = "participationIdentification.totalAmount")
    @Mapping(target = "releasedAmount" , source = "participationIdentification.releasedAmount")
    @Mapping(target = "nominalValueInC" , source = "participationIdentification.nominalValueInC")
    @Mapping(target = "numberPartInvestor" , source = "participationIdentification.numberPartInvestor")
    @Mapping(target = "totalNumberIssued" , source = "participationIdentification.totalNumberIssued")
    @Mapping(target = "investedAmountInC" , source = "participationIdentification.investedAmountInC")
    @Mapping(target = "partTotalInvestorNumber" , source = "participationIdentification.partTotalInvestorNumber")
    @Mapping(target = "totalAmountInvested" , source = "participationIdentification.totalAmountInvested")
    @Mapping(target = "methodIncrease" , source = "participationIdentification.methodIncrease")
    @Mapping(target = "questFirstTranche" , source = "participationIdentification.questFirstTranche")
    @Mapping(target = "referenceDeclaration" , source = "participationIdentification.referenceDeclaration")
    @Mapping(target = "dateDeclaration" , source = "participationIdentification.dateDeclaration")
    ParticipationIdentificationStepTwoForGetDto toDto( DirectInvestRequest directInvestRequest);
}
