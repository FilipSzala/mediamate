package com.mediamate.model.flat;

import com.mediamate.controller.profile_info.request.InitialRequest;
import com.mediamate.model.media_summary.MediaSummary;
import com.mediamate.model.meter.Meter;
import com.mediamate.model.real_estate.RealEstate;
import com.mediamate.model.renter.Renter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "real_estate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey (
                    name = "flat_real_estate_id"
            ))
    private RealEstate realEstate;
    @OneToMany(
            mappedBy = "flat",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Meter> meters = new ArrayList<>();

    @OneToOne (
            mappedBy = "flat",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private Renter renter;

    @OneToMany (fetch = FetchType.LAZY,
            mappedBy = "flat",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}
    )
    private List <MediaSummary> mediaSummaries = new ArrayList<>();

    public void addMeter(Meter meter) {
        if (!this.meters.contains(meter)) {
            this.meters.add(meter);
            meter.setFlat(this);
        }
    }
    public void addMediaSummaries (MediaSummary mediaSummary){
            if (!this.mediaSummaries.contains(mediaSummary)){
                this.mediaSummaries.add(mediaSummary);
                mediaSummary.setFlat(this);
            }
        }
    public void setRenter (InitialRequest.RenterRequest renterRequest){
            Renter renter = new Renter();
            renter.setPhoneNumber(renterRequest.getContactNumber());
            renter.setRenterCount(renterRequest.getNumberOfResidance());
            renter.setRentersFullName(renterRequest.getName());
            renter.setFlat(this);
            this.renter = renter;
    }

    public Flat(){
    }
    public Flat(Long id, RealEstate realEstate, List<Meter> meters) {
        this.id = id;
        this.realEstate = realEstate;
        this.meters = meters;
    }



}
