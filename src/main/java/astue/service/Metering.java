package astue.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Metering {
    @Scheduled(cron="0 0,12 * * *")
    public void meeter(){

    }
}
