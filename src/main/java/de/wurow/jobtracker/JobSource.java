package de.wurow.jobtracker;

import java.io.IOException;
import java.util.List;

public interface JobSource{
    List<JobOffer> search(SearchCriteria c) throws IOException, InterruptedException;
}
