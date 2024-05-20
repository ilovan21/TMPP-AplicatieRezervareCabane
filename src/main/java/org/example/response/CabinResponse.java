package org.example.response;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.model.Cabin;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class CabinResponse {
    private Long id;
    private String location;
    private BigDecimal price;
    private boolean isBooked;
    private String photo;
    private List<BookingResponse> bookings;

    public CabinResponse(Long id, String location, BigDecimal price) {
        this.id = id;
        this.location = location;
        this.price = price;
    }

    public CabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                         byte[] photoBytes , List<BookingResponse> bookings) {
        this.id = id;
        this.location = location;
        this.price = price;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.bookings = bookings;
    }
}
