package com.joaojunio.contact.experimental;

import java.util.Objects;

public class ExperimentalUserSearchRequestDTO {

    private Long userId;
    private String search;

    public ExperimentalUserSearchRequestDTO() {}

    public ExperimentalUserSearchRequestDTO(Long userId, String search) {
        this.userId = userId;
        this.search = search;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentalUserSearchRequestDTO that = (ExperimentalUserSearchRequestDTO) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getSearch(), that.getSearch());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getSearch());
    }
}
