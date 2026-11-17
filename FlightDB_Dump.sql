create table flightroutes
(
    flight_number varchar(50)  not null
        primary key,
    from_city     varchar(100) not null
);

create table arrivals
(
    id            int auto_increment
        primary key,
    flight_number varchar(50) not null,
    scheduled     varchar(50) null,
    delay_status  varchar(50) null,
    terminal      varchar(10) null,
    constraint arrivals_ibfk_1
        foreign key (flight_number) references flightroutes (flight_number)
            on delete cascade
);

create index flight_number
    on arrivals (flight_number);


