package org.example.visitor;

import org.example.model.StandardCabin;
import org.example.model.VipCabin;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;

import java.sql.SQLException;
import java.util.List;

public interface CabinVisitor {
    CabinResponse visit(StandardCabin standardCabin, byte[] photoBytes, List<BookingResponse> bookingInfo) throws SQLException;
    CabinResponse visit(VipCabin vipCabin, byte[] photoBytes, List<BookingResponse> bookingInfo) throws SQLException;
}
