package com.huawei.ibooking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.ibooking.BookingApplication;
import com.huawei.ibooking.model.SeatDO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class SeatControllerTest {
    private final String url = "/seat";
    private final String querySeatUrl = "/seat/{id}";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindAll() throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final List<SeatDO> seats = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<SeatDO>>() {
                });
        System.out.println(seats);
        Assert.assertEquals(seats.size(), 10);
    }

    @Test
    public void testCreat() throws Exception {
        final SeatDO seat = addNewSeat();
        final SeatDO queryDo = querySeat(seat);

        Assert.assertEquals(seat.getId(), queryDo.getId());
        Assert.assertEquals(seat.getName(), queryDo.getName());
        Assert.assertEquals(seat.getReservedTimeslots(), queryDo.getReservedTimeslots());
        Assert.assertEquals(seat.isFrozen(), queryDo.isFrozen());
        Assert.assertEquals(seat.isReserved(), queryDo.isReserved());
    }



    @Test
    public void testFrozen() throws Exception {
        final SeatDO seat = addNewSeat();
        seat.setFrozen(true);

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(seat))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        final SeatDO queryDo = querySeat(seat);

        Assert.assertEquals(seat.getId(), queryDo.getId());
        Assert.assertEquals(seat.getName(), queryDo.getName());
        Assert.assertEquals(seat.getReservedTimeslots(), queryDo.getReservedTimeslots());
        Assert.assertTrue(queryDo.isFrozen());
        Assert.assertEquals(seat.isReserved(), queryDo.isReserved());
    }

    @Test
    public void testFro() throws Exception {
        final SeatDO seat = addNewSeat();

        mockMvc.perform(MockMvcRequestBuilders.post("/seat/frozen/"+ seat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assert.assertEquals(querySeat(seat).isFrozen(),!seat.isFrozen());
    }
    @Test
    public void testDelete() throws Exception {
        final SeatDO seat = addNewSeat();

        mockMvc.perform(MockMvcRequestBuilders.delete("/seat/"+ seat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(querySeatUrl, seat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void querySeatByTimeslot() throws Exception {
        int timeslot = 21;
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/timeslot/"+timeslot)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final List<SeatDO> seats = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<SeatDO>>() {
                });
        System.out.println(seats);
        Assert.assertEquals(seats.size(), 9);
    }

    private SeatDO addNewSeat() throws Exception {
        final SeatDO seatDo = new SeatDO();
        seatDo.setId(11);
        seatDo.setName("test");
        seatDo.setFrozen(false);
        seatDo.setReserved(false);
        final String json = new ObjectMapper().writeValueAsString(seatDo);

        mockMvc.perform(MockMvcRequestBuilders.post("/seat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        return seatDo;
    }

    private SeatDO querySeat(SeatDO seat) throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(querySeatUrl, seat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return new ObjectMapper().readValue(
                result.getResponse().getContentAsString(), new TypeReference<SeatDO>() {
                });
    }
}