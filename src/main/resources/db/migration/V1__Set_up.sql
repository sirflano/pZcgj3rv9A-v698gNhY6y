create table sensor (
    id INT GENERATED ALWAYS AS IDENTITY,
    PRIMARY KEY(id)
);

create table data_point (
    id INT GENERATED ALWAYS AS IDENTITY,
    sensor_id INTEGER,
    date TIMESTAMP,
    temperature FLOAT,
    humidity FLOAT,
    wind_speed FLOAT,
    PRIMARY KEY(id),
    CONSTRAINT fk_sensor
        FOREIGN KEY(sensor_id)
            REFERENCES sensor(id)
);

