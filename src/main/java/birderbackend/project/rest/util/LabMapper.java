package birderbackend.project.rest.util;

import birderbackend.project.rest.security.entity.UserAuthDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import birderbackend.project.rest.entity.*;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.entity.UserDTO;

import java.util.List;
import java.util.stream.Collectors;
@Mapper(imports = Collectors.class)
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    UserDTO getUserDTO(User user);
//    List<UserDTO> getUserDTO(List<User> affiliations);

    FarmDTO getFarmDTO(Farm farm);
    List<FarmDTO> getFarmDTO(List<Farm> farm);

    @Mapping(target = "authorities", expression = "java(user.getAuthorities().stream().map(auth -> auth.getName().name()).collect(Collectors.toList()))")
    UserAuthDTO getUserAuthDTO(User user);

}
