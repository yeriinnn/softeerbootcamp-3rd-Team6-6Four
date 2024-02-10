package com.softeer.team6four.domain.payment.application;

import com.softeer.team6four.domain.payment.application.exception.InvalidChargePointException;
import com.softeer.team6four.domain.payment.application.request.ChargeRequest;
import com.softeer.team6four.domain.payment.application.response.ChargePoint;
import com.softeer.team6four.domain.payment.application.response.MyPointSummary;
import com.softeer.team6four.domain.payment.application.response.TotalPoint;
import com.softeer.team6four.domain.payment.domain.PayType;
import com.softeer.team6four.domain.payment.domain.Payment;
import com.softeer.team6four.domain.payment.domain.PaymentRepository;
import com.softeer.team6four.domain.payment.infra.PaymentRepositoryImpl;
import com.softeer.team6four.domain.reservation.application.ReservationSearchService;
import com.softeer.team6four.domain.user.application.UserSearchService;
import com.softeer.team6four.domain.user.domain.User;
import com.softeer.team6four.global.response.ErrorCode;
import com.softeer.team6four.global.response.ResponseDto;
import com.softeer.team6four.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentPointService {
    private final PaymentRepository paymentRepository;
    private final UserSearchService userSearchService;
    private final PaymentRepositoryImpl paymentRepositoryImpl;
    private final ReservationSearchService reservationSearchService;

    public ResponseDto<TotalPoint> calculateTotalPoint(Long userId){
        List<Payment> payments = paymentRepository.findByUser_UserId(userId);

        int totalPoint = 0;
        for (Payment payment : payments) {
            totalPoint += payment.getAmount();
        }

        TotalPoint point = TotalPoint.builder()
                .totalPoint(totalPoint)
                .build();

        return ResponseDto.map(HttpStatus.OK.value(), "포인트 총액(충전+수입+지출)입니다", point);
    }

    public ResponseDto<SliceResponse<MyPointSummary>> getMypointSummaryList
            (Long userId, Long lastPaymentId, Pageable pageable)
    {

        Slice<MyPointSummary> myPointSummaryList = paymentRepositoryImpl.findMyPointSummaryList(userId, lastPaymentId, pageable);
        return ResponseDto.map(HttpStatus.OK.value(), "내포인트 조회에 성공했습니다.", SliceResponse.of(myPointSummaryList));
    }

    public ResponseDto<ChargePoint> registMyPoint(Long userId, ChargeRequest chargeRequest){

        User user = userSearchService.findUserByUserId(userId);

        Integer inputPoint = chargeRequest.getChargePoint();


        if (inputPoint <= 0) {
            throw new InvalidChargePointException(ErrorCode.INVALID_CHARGE_NEGATIVE);
        }

        Payment payment = Payment.builder()
                                 .amount(inputPoint)
                                 .payType(PayType.CHARGE)
                                 .targetId(0L)
                                 .user(user)
                                 .build();
        paymentRepository.save(payment);

        ChargePoint chargePoint = ChargePoint.builder().point(inputPoint).build();

        return ResponseDto.map(HttpStatus.OK.value(), "포인트 충전되었습니다", chargePoint);
    }

}

